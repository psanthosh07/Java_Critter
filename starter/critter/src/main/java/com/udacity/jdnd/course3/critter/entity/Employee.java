package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "employee_skills", joinColumns = @JoinColumn(name = "employee_id"))
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> skills = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "employee_availability", joinColumns = @JoinColumn(name = "employee_id"))
    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> available = new HashSet<>();

    // Utility method to add a skill
    public void addSkill(EmployeeSkill skill) {
        if (skills == null) skills = new HashSet<>();
        skills.add(skill);
    }

    // Utility method to set availability for a single day
    public void addAvailability(DayOfWeek day) {
        if (available == null) available = new HashSet<>();
        available.add(day);
    }

    // Check if employee is available on a specific day
    public boolean isAvailableOn(DayOfWeek day) {
        return available != null && available.contains(day);
    }

    // Check if employee has all required skills
    public boolean hasSkills(Set<EmployeeSkill> requiredSkills) {
        return skills != null && skills.containsAll(requiredSkills);
    }
}
