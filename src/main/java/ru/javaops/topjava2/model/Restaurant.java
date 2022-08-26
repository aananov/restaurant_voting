package ru.javaops.topjava2.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.javaops.topjava2.HasId;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor
public class Restaurant extends NamedEntity implements HasId, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    @OneToMany(mappedBy = "restaurant")
    private List<Meal> meals;


}
