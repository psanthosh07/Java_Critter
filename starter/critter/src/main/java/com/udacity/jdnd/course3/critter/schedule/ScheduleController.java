package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.PetsData;
import com.udacity.jdnd.course3.critter.entity.Schedules;
import com.udacity.jdnd.course3.critter.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;
    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedules schedule = new Schedules();
        schedule.setScheduledate(scheduleDTO.getDate());
        schedule.setActivities(scheduleDTO.getActivities());
        return getScheduleDTO(scheduleService.saveSchedule(schedule, scheduleDTO.getEmployeeIds(), scheduleDTO.getPetIds()));

    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedules> schedules = scheduleService.getAllSchedules();
        return schedules.stream().map(this::getScheduleDTO).collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedules> schedules = scheduleService.getAllSchedulesForPet(petId);
        return schedules.stream().map(this::getScheduleDTO).collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedules> schedules = scheduleService.getAllSchedulesForEmployee(employeeId);
        return schedules.stream().map(this::getScheduleDTO).collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedules> schedules = scheduleService.getAllSchedulesForCustomer(customerId);
        return schedules.stream().map(this::getScheduleDTO).collect(Collectors.toList());
    }
    private ScheduleDTO getScheduleDTO(Schedules schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setEmployeeIds(schedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList()));
        scheduleDTO.setPetIds(schedule.getPets().stream().map(PetsData::getId).collect(Collectors.toList()));
        scheduleDTO.setDate(schedule.getScheduledate());
        scheduleDTO.setActivities(schedule.getActivities());
        return scheduleDTO;
    }
}
