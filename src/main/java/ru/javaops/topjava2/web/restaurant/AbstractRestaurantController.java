package ru.javaops.topjava2.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.to.RestaurantTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
public class AbstractRestaurantController {

    @Autowired
    protected RestaurantRepository repository;

    public ResponseEntity<List<Restaurant>> getAllOnDate(LocalDate localDate) {
        List<Restaurant> allHavingMealsForDate = repository.getAllHavingMealsForDate(localDate);
        if (allHavingMealsForDate.size() > 0) {
            return new ResponseEntity<>(allHavingMealsForDate, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<RestaurantTo> get(int restaurantId) {
        log.info("get id={}", restaurantId);
        Optional<Restaurant> found = repository.findById(restaurantId);
        if (found.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new RestaurantTo(found.get()), HttpStatus.OK);
    }

    public ResponseEntity<Restaurant> getWithMeals(LocalDate localDate, int restaurantId) {
        log.info("getWithMeals for id={} on date {}", restaurantId, localDate);
        return ResponseEntity.of(repository.getWithMeals(restaurantId, localDate));
    }
}