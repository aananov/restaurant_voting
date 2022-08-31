package ru.javaops.topjava2.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava2.error.IllegalRequestDataException;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.repository.VoteRepository;
import ru.javaops.topjava2.web.AuthUser;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class VoteController {
    public static final String REST_URL = "/api/votes";
    public static final LocalTime voteEndTime = LocalTime.of(18, 0);

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<Vote> createWithLocation(@RequestParam int restaurantId,
                                                   @AuthenticationPrincipal AuthUser authUser) {

        log.info("createWithLocation for restaurant id={} and user id={}", restaurantId, authUser.id());
        checkVoteConditions(restaurantId);
        Vote created = voteRepository.save(authUser.id(), LocalDate.now(), restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@RequestParam int restaurantId,
                       @AuthenticationPrincipal AuthUser authUser) {
        log.info("update for restaurant id={} and user id={}", restaurantId, authUser.id());
        checkVoteConditions(restaurantId);
        Vote toBeUpdated = voteRepository.get(authUser.id(), LocalDate.now())
                .orElseThrow(() -> new IllegalRequestDataException("User with id=" + authUser.id() + " hasn't voted yet"));
        toBeUpdated.setRestaurantId(restaurantId);
    }

    private void checkVoteConditions(int restaurantId) {
        checkVoteTime();
        checkRestaurant(restaurantId);
    }

    private void checkVoteTime() {
        if (LocalTime.now().isAfter(voteEndTime)) {
            throw new IllegalRequestDataException("vote ended");
        }
    }

    private void checkRestaurant(int restaurantId) {
        restaurantRepository.getExisting(restaurantId);
        restaurantRepository.getHavingMealsForDate(restaurantId, LocalDate.now())
                .orElseThrow(() -> new IllegalRequestDataException(
                        "Restaurant with id=" + restaurantId + " has no meals for today"));
    }
}