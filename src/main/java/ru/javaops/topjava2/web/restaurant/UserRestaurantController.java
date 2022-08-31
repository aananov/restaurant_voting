package ru.javaops.topjava2.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.to.RestaurantToWithVotes;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = UserRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "restaurants")
public class UserRestaurantController extends AbstractRestaurantController {

    public static final String REST_URL = "/api/restaurants";

    public static final LocalDate today = LocalDate.now();

    @GetMapping
    @Cacheable
    public ResponseEntity<List<RestaurantToWithVotes>> getAll() {
        return super.getAllOnDate(today, false);
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantToWithVotes> get(@PathVariable int restaurantId) {
        return super.get(restaurantId, today, false);
    }

    @GetMapping("/{restaurantId}/with-meals")
    public ResponseEntity<Restaurant> getWithMeals(@PathVariable int restaurantId) {
        return super.getWithMeals(LocalDate.now(), restaurantId);
    }
}