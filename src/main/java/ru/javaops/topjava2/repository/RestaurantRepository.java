package ru.javaops.topjava2.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Query("SELECT distinct r FROM Restaurant r join r.meals m where m.lunchDate=:date")
    List<Restaurant> getAllHavingMealsForDate(LocalDate date);

    @Query("SELECT distinct r FROM Restaurant r join r.meals m where r.id=:id and m.lunchDate=:date")
    Optional<Restaurant> getHavingMealsForDate(int id, LocalDate date);

    @Query("SELECT r from Restaurant r join fetch r.meals m where r.id=:id and m.lunchDate=:date")
    Optional<Restaurant> getWithMeals(int id, LocalDate date);

}