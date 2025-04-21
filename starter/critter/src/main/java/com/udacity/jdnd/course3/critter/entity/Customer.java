package com.udacity.jdnd.course3.critter.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String number;

    private String notes;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "customer",
            targetEntity = PetsData.class,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<PetsData> li = new ArrayList<>();

    public void save(PetsData pet) {
        if (li == null) {
            li = new ArrayList<>();
        }
        li.add(pet);
        pet.setCustomer(this);
    }
}
