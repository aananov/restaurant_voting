package ru.javaops.topjava2.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.to.RestaurantTo;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = UserRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class UserRestaurantController extends AbstractRestaurantController {

    public static final String REST_URL = "/api/restaurants";

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAll() {
        log.info("getAll");
        return super.getAllOnDate(LocalDate.now());
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantTo> get(@PathVariable int restaurantId) {
        return super.get(restaurantId);
    }

    @GetMapping("/{restaurantId}/with-meals")
    public ResponseEntity<Restaurant> getWithMeals(@PathVariable int restaurantId) {
        return super.getWithMeals(LocalDate.now(), restaurantId);
    }
}