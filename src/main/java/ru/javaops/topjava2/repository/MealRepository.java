package ru.javaops.topjava2.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.error.IllegalRequestDataException;
import ru.javaops.topjava2.model.Meal;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface MealRepository extends BaseRepository<Meal> {

    Optional<Meal> findByIdAndRestaurantId(int id, int restaurantId);

    List<Meal> findAllByRestaurantIdAndLunchDate(int restaurantId, LocalDate lunchDate);

    List<Meal> findAllByRestaurantId(int restaurantId);

    default Meal checkExistsAndBelong(int id, int restaurantId) {
        findById(id).orElseThrow(
                () -> new IllegalRequestDataException("Entity with id=" + id + " not found"));
        return findByIdAndRestaurantId(id, restaurantId).orElseThrow(
                () -> new IllegalRequestDataException("Meal id=" + id + " doesn't belong to Restaurant id=" + restaurantId));
    }
}