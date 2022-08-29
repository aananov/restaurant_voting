package ru.javaops.topjava2.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import ru.javaops.topjava2.HasId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "meal", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"restaurant_id", "lunch_date", "description"},
                name = "uk_restaurant_lunch_date_description")})
@Getter
@Setter
@NoArgsConstructor
public class Meal extends BaseEntity implements HasId, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "description", nullable = false)
    @NotBlank
    @Length(min = 3, max = 100)
    private String description;

    @Column(name = "price", nullable = false)
    @Range(min = 100)
    private int price;

    @Column(name = "lunch_date", nullable = false)
    @NotNull
    private LocalDate lunchDate;

    @Column(name = "restaurant_id", nullable = false)
    private int restaurantId;

    public Meal(Integer id, String description, int price, LocalDate lunchDate) {
        super(id);
        this.description = description;
        this.price = price;
        this.lunchDate = lunchDate;
    }
}
