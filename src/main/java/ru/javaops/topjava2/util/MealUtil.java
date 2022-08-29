package ru.javaops.topjava2.util;

import ru.javaops.topjava2.model.Meal;
import ru.javaops.topjava2.to.MealTo;

public class MealUtil {

    public static Meal createNewFromTo(MealTo mealTo) {
        return new Meal(mealTo.getId(), mealTo.getDescription(), mealTo.getPrice(), mealTo.getLunchDate());
    }

    public static Meal updateFromTo(MealTo mealTo, Meal meal) {
        meal.setDescription(mealTo.getDescription());
        meal.setPrice(mealTo.getPrice());
        meal.setLunchDate(mealTo.getLunchDate());
        return meal;
    }
}