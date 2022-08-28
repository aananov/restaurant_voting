package ru.javaops.topjava2.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava2.error.IllegalRequestDataException;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.to.RestaurantTo;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static ru.javaops.topjava2.util.RestaurantUtil.createNewFromTo;
import static ru.javaops.topjava2.util.RestaurantUtil.updateFromTo;
import static ru.javaops.topjava2.util.validation.ValidationUtil.assureIdConsistent;
import static ru.javaops.topjava2.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class RestaurantController {
    static final String REST_URL = "/api/restaurants";

    @Autowired
    RestaurantRepository repository;

    @GetMapping
    public List<Restaurant> getAll(@RequestParam Optional<LocalDate> localDate) {
        log.info("getAll on date {}", getDateForRequest(localDate));
        return repository.getAllHavingMealsForDate(getDateForRequest(localDate));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete id={}", id);
        repository.deleteExisted(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@RequestParam Optional<LocalDate> localDate, @PathVariable int id) {
        log.info("get id={} on date {}", id, getDateForRequest(localDate));
        return ResponseEntity.of(repository.getHavingMealsForDate(id, getDateForRequest(localDate)));
    }

    @GetMapping("/{id}/with-meals")
    public ResponseEntity<Restaurant> getWithMeals(@RequestParam Optional<LocalDate> localDate, @PathVariable int id) {
        log.info("getWithMeals for id={} on date {}", id, getDateForRequest(localDate));
        return ResponseEntity.of(repository.getWithMeals(id, getDateForRequest(localDate)));
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

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@Valid @RequestBody RestaurantTo restaurantTo, @PathVariable int id) {
        log.info("update {} with id={}", restaurantTo, id);
        assureIdConsistent(restaurantTo, id);
        Restaurant toBeUpdated = repository.findById(id)
                .orElseThrow(() -> new IllegalRequestDataException("Entity with id=" + id + " not found"));
        updateFromTo(restaurantTo, toBeUpdated);
    }

    private LocalDate getDateForRequest(Optional<LocalDate> localDate) {
        return localDate.orElse(LocalDate.now());
    }
}