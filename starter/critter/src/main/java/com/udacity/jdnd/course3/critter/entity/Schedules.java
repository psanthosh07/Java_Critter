package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "schedules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Schedules implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToMany(targetEntity = Employee.class)
    private List<Employee> employees;

    @ManyToMany(targetEntity = PetsData.class)
    private List<PetsData> pets;

    private LocalDate scheduledate;

    @ElementCollection
    private Set<EmployeeSkill> activities;

    // Utility method to check if the schedule contains a specific pet
    public boolean hasPet(PetsData pet) {
        return pets.contains(pet);
    }

    // Utility method to check if the schedule contains a specific employee
    public boolean hasEmployee(Employee employee) {
        return employees.contains(employee);
    }

    // Utility method to add a pet to the schedule
    public void addPet(PetsData pet) {
        if (!pets.contains(pet)) {
            pets.add(pet);
        }
    }

    // Utility method to add an employee to the schedule
    public void addEmployee(Employee employee) {
        if (!employees.contains(employee)) {
            employees.add(employee);
        }
    }

    // Utility method to check if a given activity is scheduled
    public boolean hasActivity(EmployeeSkill activity) {
        return activities.contains(activity);
    }

    // Utility method to add an activity to the schedule
    public void addActivity(EmployeeSkill activity) {
        activities.add(activity);
    }

    // Utility method to get the number of pets scheduled
    public int getNumberOfPets() {
        return pets.size();
    }

    // Utility method to get the number of employees scheduled
    public int getNumberOfEmployees() {
        return employees.size();
    }
}
