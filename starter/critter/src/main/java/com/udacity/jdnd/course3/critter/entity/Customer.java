package com.udacity.jdnd.course3.critter.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String number;

    private String notes;

    @OneToMany(targetEntity = PetsData.class,cascade = CascadeType.ALL, mappedBy = "customer")
    private List<PetsData> li = new ArrayList<>();

    public  void save(PetsData pet){
      li.add(pet);
      pet.setCustomer(this);
    }


}
