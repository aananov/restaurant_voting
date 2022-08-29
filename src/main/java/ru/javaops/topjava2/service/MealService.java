package ru.javaops.topjava2.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.Meal;
import ru.javaops.topjava2.repository.MealRepository;
import ru.javaops.topjava2.repository.RestaurantRepository;

@Service
@AllArgsConstructor
public class MealService {

    private final MealRepository mealRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public Meal save(Meal meal, int restaurantId) {
        meal.setRestaurant(restaurantRepository.getExisting(restaurantId));
        return mealRepository.save(meal);
    }
}