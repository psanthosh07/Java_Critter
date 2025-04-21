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

    public List<Schedules> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedules> getAllSchedulesForPet(long petId) {
        PetsData pet =petRepository.getReferenceById(petId);
        return scheduleRepository.getAllByPetsContains(pet);
    }

    public List<Schedules> getAllSchedulesForEmployee(long employeeId) {
        Employee employee = employeeRepository.getReferenceById(employeeId);
        return scheduleRepository.getAllByEmployeesContains(employee);
    }

    public List<Schedules> getAllSchedulesForCustomer(long customerId) {
        Customer customer = customerRepository.getReferenceById(customerId);
        return  scheduleRepository.getAllByPetsIn(customer.getLi());
    }

    public Schedules saveSchedule(Schedules schedule, List<Long> employeeIds, List<Long> petIds) {
        List<Employee> employees = employeeRepository.findAllById(employeeIds);
        List<PetsData> pets = petRepository.findAllById(petIds);
        schedule.setEmployees(employees);
        schedule.setPets(pets);
        return scheduleRepository.save(schedule);
    }
}
