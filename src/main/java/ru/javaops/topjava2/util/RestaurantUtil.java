package ru.javaops.topjava2.util;

import lombok.experimental.UtilityClass;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.to.RestaurantTo;

@UtilityClass
public class RestaurantUtil {

    public static Restaurant createNewFromTo(RestaurantTo restaurantTo) {
        return new Restaurant(restaurantTo.getId(), restaurantTo.getName());
    }

    public static Restaurant updateFromTo(RestaurantTo restaurantTo, Restaurant restaurant) {
        restaurant.setName(restaurantTo.getName());
        return restaurant;
    }
}
