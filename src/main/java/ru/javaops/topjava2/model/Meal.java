package ru.javaops.topjava2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import ru.javaops.topjava2.HasId;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "meal", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"restaurant_id", "lunch_date", "description"},
                name = "meal_uk_restaurant_lunch_date_description")})
@Getter
@Setter
@NoArgsConstructor
public class Meal extends BaseEntity implements HasId, Serializable {

    //TODO сделать однонаправленную связь
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    @JsonBackReference
    private Restaurant restaurant;

    public Meal(Integer id, String description, int price, LocalDate lunchDate) {
        super(id);
        this.description = description;
        this.price = price;
        this.lunchDate = lunchDate;
    }
}
