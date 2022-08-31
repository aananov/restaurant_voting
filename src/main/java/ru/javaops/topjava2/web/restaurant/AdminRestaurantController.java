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
    public ResponseEntity<List<Restaurant>> getAll(@RequestParam Optional<LocalDate> localDate) {
        log.info("getAll on date {}", getDateForRequest(localDate));
        return super.getAllOnDate(getDateForRequest(localDate));
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantTo> get(@PathVariable int restaurantId) {
        return super.get(restaurantId);
    }

    @GetMapping("/{restaurantId}/with-meals")
    public ResponseEntity<Restaurant> getWithMeals(@RequestParam Optional<LocalDate> localDate,
                                                   @PathVariable int restaurantId) {
        return super.getWithMeals(getDateForRequest(localDate), restaurantId);
    }

    @DeleteMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId) {
        log.info("delete id={}", restaurantId);
        repository.deleteExisted(restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@RequestBody @Valid RestaurantTo restaurantTo) {
        log.info("create {}", restaurantTo);
        checkNew(restaurantTo);
        Restaurant created = repository.save(createNewFromTo(restaurantTo));
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
        Restaurant toBeUpdated = repository.findById(restaurantId)
                .orElseThrow(() -> new IllegalRequestDataException("Entity with id=" + restaurantId + " not found"));
        updateFromTo(restaurantTo, toBeUpdated);
    }
}