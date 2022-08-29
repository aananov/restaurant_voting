package ru.javaops.topjava2.to;

import lombok.Value;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import ru.javaops.topjava2.HasId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Value
public class MealTo extends BaseTo implements HasId {

    @NotBlank
    @Length(min = 3, max = 100)
    String description;

    @Range(min = 100)
    Integer price;

    @NotNull
    LocalDate lunchDate;
}