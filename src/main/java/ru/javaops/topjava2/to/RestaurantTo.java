package ru.javaops.topjava2.to;

import lombok.Value;
import ru.javaops.topjava2.HasId;

@Value
public class RestaurantTo extends NamedTo implements HasId {

    public RestaurantTo(Integer id, String name) {
        super(id, name);
    }
}
