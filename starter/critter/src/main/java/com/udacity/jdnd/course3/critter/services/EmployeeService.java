package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Fetch employee by ID with error handling
    public Employee getEmployeeById(long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
    }

    // Fetch employees available for a given service date and skills
    public List<Employee> getEmployeeForService(LocalDate date, Set<EmployeeSkill> skill) {
        return employeeRepository
                .getAllByAvailableContains(date.getDayOfWeek()).stream()
                .filter(employee -> employee.getSkills().containsAll(skill))
                .collect(Collectors.toList());
    }

    // Save an employee to the database, includes basic validation
    public Employee saveEmployee(Employee employee) {
        if (employee == null || employee.getName() == null || employee.getName().isEmpty()) {
            throw new IllegalArgumentException("Employee name cannot be null or empty.");
        }
        return employeeRepository.save(employee);
    }

    // Set the availability of an employee and save the updated availability
    public void setEmployeeAvailability(Set<DayOfWeek> available, long employeeId) {
        Employee employee = getEmployeeById(employeeId);
        if (available == null || available.isEmpty()) {
            throw new IllegalArgumentException("Availability cannot be null or empty.");
        }
        employee.setAvailable(available);
        employeeRepository.save(employee);
    }

    // Fetch all employees with specific skill set and available on a particular day
    public List<Employee> getAvailableEmployeesBySkillAndDay(Set<EmployeeSkill> skills, DayOfWeek dayOfWeek) {
        return employeeRepository
                .getAllByAvailableContains(dayOfWeek).stream()
                .filter(employee -> employee.getSkills().containsAll(skills))
                .collect(Collectors.toList());
    }

    // Update employee skills
    public Employee updateEmployeeSkills(long employeeId, Set<EmployeeSkill> newSkills) {
        Employee employee = getEmployeeById(employeeId);
        employee.setSkills(newSkills);
        return employeeRepository.save(employee);
    }
}
