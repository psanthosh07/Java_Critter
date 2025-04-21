package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.PetsData;
import com.udacity.jdnd.course3.critter.entity.Schedules;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedules,Long> {
    List<Schedules> getAllByPetsContains(PetsData pet);

    List<Schedules> getAllByEmployeesContains(Employee employee);

    List<Schedules> getAllByPetsIn(List<PetsData> pets);
}

