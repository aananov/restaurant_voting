package ru.javaops.topjava2.web.meal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava2.model.Meal;
import ru.javaops.topjava2.repository.MealRepository;
import ru.javaops.topjava2.service.MealService;
import ru.javaops.topjava2.to.MealTo;
import ru.javaops.topjava2.web.restaurant.RestaurantController;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static ru.javaops.topjava2.util.DateAndTimeUtil.getDateForRequest;
import static ru.javaops.topjava2.util.MealUtil.createNewFromTo;
import static ru.javaops.topjava2.util.MealUtil.updateFromTo;
import static ru.javaops.topjava2.util.validation.ValidationUtil.assureIdConsistent;
import static ru.javaops.topjava2.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = MealController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class MealController {

    static final String REST_URL = RestaurantController.REST_URL + "{restaurantId}/meals";

    @Autowired
    MealRepository repository;

    @Autowired
    MealService service;

    @GetMapping
    ResponseEntity<List<Meal>> getAll(@PathVariable int restaurantId,
                                      @RequestParam Optional<LocalDate> localDate,
                                      @RequestParam(defaultValue = "false") boolean isDateConsidered) {
        String logMessage = "getAll for RestaurantId={}" + (isDateConsidered ? "" : " on date {}");
        log.info(logMessage, restaurantId, getDateForRequest(localDate));
        List<Meal> mealsFound;
        if (isDateConsidered) {
            mealsFound = repository.findAllByRestaurantIdAndLunchDate(restaurantId, getDateForRequest(localDate));
        } else {
            mealsFound = repository.findAllByRestaurantId(restaurantId);
        }
        return mealsFound.size() > 0 ? new ResponseEntity<>(mealsFound, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{mealId}")
    ResponseEntity<Meal> get(@PathVariable int restaurantId, @PathVariable int mealId) {
        log.info("get id={} for RestaurantId={}",
                mealId, restaurantId);
        return ResponseEntity.of(repository.findByIdAndRestaurantId(mealId, restaurantId));
    }

    @DeleteMapping("/{mealId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId, @PathVariable int mealId) {
        log.info("delete id={}", mealId);
        repository.checkExistsAndBelong(mealId, restaurantId);
        repository.delete(mealId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> create(@PathVariable int restaurantId, @RequestBody @Valid MealTo mealTo) {
        log.info("create {} for restaurant id={}", mealTo, restaurantId);
        checkNew(mealTo);
        Meal created = service.save(createNewFromTo(mealTo), restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{mealId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@Valid @RequestBody MealTo mealTo, @PathVariable int restaurantId, @PathVariable int mealId) {
        log.info("update {} with id={} for restaurant id={}", mealTo, mealId, restaurantId);
        assureIdConsistent(mealTo, mealId);
        Meal toBeUpdated = repository.checkExistsAndBelong(mealId, restaurantId);
        updateFromTo(mealTo, toBeUpdated);
    }
}