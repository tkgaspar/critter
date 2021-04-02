package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.Entities.Employee;
import com.udacity.jdnd.course3.critter.Entities.Schedule;
import com.udacity.jdnd.course3.critter.Service.CustomerService;
import com.udacity.jdnd.course3.critter.Service.EmployeeService;
import com.udacity.jdnd.course3.critter.Service.PetService;
import com.udacity.jdnd.course3.critter.Service.ScheduleService;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;
    @Autowired
    PetService petService;
    @Autowired
    CustomerService customerService;
    @Autowired
    EmployeeService employeeService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule(scheduleDTO.getEmployeeIds(), scheduleDTO.getPetIds(), scheduleDTO.getDate(), scheduleDTO.getActivities());
        scheduleService.saveSchedule(schedule);
        return convertScheduleToScheduleDTO(scheduleService.findById(schedule.getId()));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<ScheduleDTO> allSchedules = new ArrayList<>();
        scheduleService.findAll().forEach(i ->
                allSchedules.add(convertScheduleToScheduleDTO(i)));
        return allSchedules;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<ScheduleDTO> allSchedules = new ArrayList<>();
        scheduleService.findByPet(petService.findById(petId))
                .forEach(i -> allSchedules.add(convertScheduleToScheduleDTO(i)));
        return allSchedules;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<ScheduleDTO> allSchedules = new ArrayList<>();
        scheduleService.findByEmployee(employeeService.findById(employeeId))
                .forEach(i -> allSchedules.add(convertScheduleToScheduleDTO(i)));
        return allSchedules;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<ScheduleDTO> allSchedules = new ArrayList<>();
        scheduleService.findByCustomer(customerService.findById(customerId))
                .forEach(i -> allSchedules.add(convertScheduleToScheduleDTO(i)));
        return allSchedules;
    }

    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        return scheduleDTO;
    }
}
