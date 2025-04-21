package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.pet.PetType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "pets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PetsData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = Customer.class, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(name = "pet_type")
    private PetType petType;

    private String name;

    private LocalDate birth;

    private String notes;

    // Utility method to update pet's notes
    public void updateNotes(String newNotes) {
        this.notes = newNotes;
    }

    // Check if pet is older than a certain number of years
    public boolean isOlderThan(int years) {
        return LocalDate.now().minusYears(years).isAfter(birth);
    }

    // Utility method to update pet's type
    public void updatePetType(PetType newType) {
        this.petType = newType;
    }

    // Utility method to calculate pet's age in years
    public int getAgeInYears() {
        return LocalDate.now().getYear() - birth.getYear();
    }
}
