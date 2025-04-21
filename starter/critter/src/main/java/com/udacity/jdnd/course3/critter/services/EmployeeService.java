package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee getEmployeeById(long id){
       return employeeRepository.getReferenceById(id);
    }

    public List<Employee> getEmployeeForService(LocalDate date, Set<EmployeeSkill> skill){
        List<Employee> employees = employeeRepository
                .getAllByAvailableContains(date.getDayOfWeek()).stream()
                .filter(employee -> employee.getSkills().containsAll(skill))
                .collect(Collectors.toList());
        return employees;
    }
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void setEmployeeAvailability(Set<DayOfWeek> Available, long employeeId) {
        Employee employee = employeeRepository.getReferenceById(employeeId);
        employee.setAvailable(Available);
        employeeRepository.save(employee);
    }
}

