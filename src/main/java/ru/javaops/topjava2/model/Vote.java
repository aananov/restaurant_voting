package ru.javaops.topjava2.model;

import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "vote", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"vote_date", "user_id"},
                name = "vote_uk_user_id_vote_date")})
@NoArgsConstructor
public class Vote extends BaseEntity {

    @Column(name = "vote_date", nullable = false)
    @NotNull
    private LocalDate voteDate;

    @Column(name = "user_id", nullable = false)
    @NotNull
    @Range(min = 1)
    private Integer userId;

    @Column(name = "restaurant_id", nullable = false)
    @NotNull
    @Range(min = 1)
    private Integer restaurantId;

    public Vote(LocalDate voteDate, Integer userId, Integer restaurantId) {
        this.voteDate = voteDate;
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }
}