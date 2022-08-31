package ru.javaops.topjava2.to;

import lombok.Value;
import ru.javaops.topjava2.model.Restaurant;

@Value
public class RestaurantToWithVotes extends NamedTo {

    int votesCount;

    public RestaurantToWithVotes(Restaurant restaurant, int votesCount) {
        super(restaurant.getId(), restaurant.getName());
        this.votesCount = votesCount;
    }
}