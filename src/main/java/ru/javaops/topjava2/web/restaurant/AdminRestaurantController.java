package ru.javaops.topjava2.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava2.error.IllegalRequestDataException;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.to.RestaurantTo;
import ru.javaops.topjava2.to.RestaurantToWithVotes;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static ru.javaops.topjava2.util.DateAndTimeUtil.getDateForRequest;
import static ru.javaops.topjava2.util.RestaurantUtil.createNewFromTo;
import static ru.javaops.topjava2.util.RestaurantUtil.updateFromTo;
import static ru.javaops.topjava2.util.validation.ValidationUtil.assureIdConsistent;
import static ru.javaops.topjava2.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminRestaurantController extends AbstractRestaurantController {
    public static final String REST_URL = "/api/admin/restaurants";

    @GetMapping
    public ResponseEntity<List<RestaurantToWithVotes>> getAll(@RequestParam Optional<LocalDate> localDate,
                                                              @RequestParam(defaultValue = "false") boolean allDatesSearch) {
        return super.getAllOnDate(getDateForRequest(localDate), allDatesSearch);
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantToWithVotes> get(@PathVariable int restaurantId,
                                                     @RequestParam Optional<LocalDate> localDate,
                                                     @RequestParam(defaultValue = "false") boolean allDatesSearch) {
        return super.get(restaurantId, getDateForRequest(localDate), allDatesSearch);
    }

    @GetMapping("/{restaurantId}/with-meals")
    public ResponseEntity<Restaurant> getWithMeals(@PathVariable int restaurantId,
                                                   @RequestParam Optional<LocalDate> localDate) {
        return super.getWithMeals(getDateForRequest(localDate), restaurantId);
    }

    @DeleteMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId) {
        log.info("delete id={}", restaurantId);
        restaurantRepository.deleteExisted(restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@RequestBody @Valid RestaurantTo restaurantTo) {
        log.info("create {}", restaurantTo);
        checkNew(restaurantTo);
        Restaurant created = restaurantRepository.save(createNewFromTo(restaurantTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@Valid @RequestBody RestaurantTo restaurantTo, @PathVariable int restaurantId) {
        log.info("update {} with id={}", restaurantTo, restaurantId);
        assureIdConsistent(restaurantTo, restaurantId);
        Restaurant toBeUpdated = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalRequestDataException("Entity with id=" + restaurantId + " not found"));
        updateFromTo(restaurantTo, toBeUpdated);
    }
}