package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.PetsData;
import com.udacity.jdnd.course3.critter.entity.Schedules;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;

    // Fetch all schedules from the repository
    public List<Schedules> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    // Fetch all schedules for a specific pet by pet ID
    public List<Schedules> getAllSchedulesForPet(long petId) {
        PetsData pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found with ID: " + petId));
        return scheduleRepository.getAllByPetsContains(pet);
    }

    // Fetch all schedules for a specific employee by employee ID
    public List<Schedules> getAllSchedulesForEmployee(long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + employeeId));
        return scheduleRepository.getAllByEmployeesContains(employee);
    }

    // Fetch all schedules for a specific customer by customer ID
    public List<Schedules> getAllSchedulesForCustomer(long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));
        return scheduleRepository.getAllByPetsIn(customer.getLi());
    }

    // Save a new schedule along with associated employees and pets
    public Schedules saveSchedule(Schedules schedule, List<Long> employeeIds, List<Long> petIds) {
        List<Employee> employees = employeeRepository.findAllById(employeeIds);
        List<PetsData> pets = petRepository.findAllById(petIds);

        // Validate that the lists are not empty
        if (employees.isEmpty()) {
            throw new IllegalArgumentException("No employees found with the provided IDs.");
        }
        if (pets.isEmpty()) {
            throw new IllegalArgumentException("No pets found with the provided IDs.");
        }

        schedule.setEmployees(employees);
        schedule.setPets(pets);
        return scheduleRepository.save(schedule);
    }
}
