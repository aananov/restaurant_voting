package ru.javaops.topjava2.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.repository.VoteRepository;
import ru.javaops.topjava2.to.RestaurantToWithVotes;
import ru.javaops.topjava2.util.RestaurantUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
public class AbstractRestaurantController {

    @Autowired
    protected RestaurantRepository restaurantRepository;

    @Autowired
    private VoteRepository voteRepository;

    public ResponseEntity<List<RestaurantToWithVotes>> getAllOnDate(LocalDate localDate, boolean allDatesSearch) {
        log.info(allDatesSearch ? "getAll" : "getAll on date {}", localDate);

        List<Restaurant> found;
        if (allDatesSearch) {
            found = restaurantRepository.findAll();
        } else {
            found = restaurantRepository.getAllHavingMealsForDate(localDate);
        }

        if (found.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(RestaurantUtil.getWithVotes(found, votes(localDate)), HttpStatus.OK);
    }

    public ResponseEntity<RestaurantToWithVotes> get(int restaurantId, LocalDate localDate, boolean allDatesSearch) {
        log.info("get id={} on date {}", restaurantId, localDate);

        Optional<Restaurant> found;
        if (allDatesSearch) {
            found = restaurantRepository.findById(restaurantId);
        } else {
            found = restaurantRepository.getHavingMealsForDate(restaurantId, localDate);
        }

        if (found.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(RestaurantUtil.createOneWithVotes(found.get(), votes(localDate)), HttpStatus.OK);
    }

    public ResponseEntity<Restaurant> getWithMeals(LocalDate localDate, int restaurantId) {
        log.info("getWithMeals for id={} on date {}", restaurantId, localDate);
        return ResponseEntity.of(restaurantRepository.getWithMeals(restaurantId, localDate));
    }

    private List<Vote> votes(LocalDate localDate) {
        return voteRepository.findAllByVoteDate(localDate);
    }
}