package ru.javaops.topjava2.util;

import lombok.experimental.UtilityClass;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.to.RestaurantTo;
import ru.javaops.topjava2.to.RestaurantToWithVotes;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@UtilityClass
public class RestaurantUtil {

    public static Restaurant createNewFromTo(RestaurantTo restaurantTo) {
        return new Restaurant(restaurantTo.getId(), restaurantTo.getName());
    }

    public static Restaurant updateFromTo(RestaurantTo restaurantTo, Restaurant restaurant) {
        restaurant.setName(restaurantTo.getName());
        return restaurant;
    }

    public static RestaurantToWithVotes createOneWithVotes(Restaurant restaurant, Collection<Vote> votes) {
        long count = votes.stream().filter(v -> Objects.equals(restaurant.getId(), v.getRestaurantId())).count();
        return new RestaurantToWithVotes(restaurant, (int) count);
    }

    public static List<RestaurantToWithVotes> getWithVotes(Collection<Restaurant> restaurants, Collection<Vote> votes) {
        return restaurants.stream()
                .map(r -> createOneWithVotes(r, votes))
                .sorted(Comparator.comparingInt(RestaurantToWithVotes::getVotesCount).reversed())
                .collect(Collectors.toList());
    }
}