package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.Entities.Employee;
import com.udacity.jdnd.course3.critter.Entities.Pet;
import com.udacity.jdnd.course3.critter.Entities.Schedule;
import com.udacity.jdnd.course3.critter.Repositories.ScheduleRepository;
import com.udacity.jdnd.course3.critter.Service.CustomerService;
import com.udacity.jdnd.course3.critter.Service.EmployeeService;
import com.udacity.jdnd.course3.critter.Service.PetService;
import com.udacity.jdnd.course3.critter.Service.ScheduleService;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    ScheduleRepository scheduleRepository;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule newSchedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, newSchedule);
        List<Long> employeeIds = scheduleDTO.getEmployeeIds();
        List<Long> petIds = scheduleDTO.getPetIds();
        List<Employee> employeeList = new ArrayList<>();
        List<Pet> petList = new ArrayList<>();
        if (employeeIds != null) {
            for (Long id : employeeIds) {
                employeeList.add(employeeService.findById(id));
            }
        }
        if (petIds != null) {
            for (Long id : petIds) {
                petList.add(petService.findById(id));
            }
        }
        newSchedule.setEmployeeIds(employeeList);
        newSchedule.setPetIds(petList);
        newSchedule.setActivities(scheduleDTO.getActivities());
        scheduleService.saveSchedule(newSchedule);

        return scheduleDTO;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<ScheduleDTO> allScheduleDTOs = new ArrayList<>();
        List<Schedule> allSchedules = scheduleService.findAll();
        return convertSchedulesToScheduleDTOs(allSchedules,allScheduleDTOs);

    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<ScheduleDTO> allScheduleDTOs = new ArrayList<>();
        List<Schedule>allSchedules=scheduleRepository.findByPetIds_Id(petId);
        return convertSchedulesToScheduleDTOs(allSchedules,allScheduleDTOs);
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> allSchedules = scheduleRepository.findByEmployeeIds_Id(employeeId);
        List<ScheduleDTO> allScheduleDTOs = new ArrayList<>();
        return convertSchedulesToScheduleDTOs(allSchedules,allScheduleDTOs);

    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> allSchedules = scheduleRepository.findByCustomerIds_Id(customerId);
        List<ScheduleDTO> allScheduleDTOs = new ArrayList<>();
        return convertSchedulesToScheduleDTOs(allSchedules,allScheduleDTOs);
    }

    private List<ScheduleDTO> convertSchedulesToScheduleDTOs(List<Schedule>allSchedules, List<ScheduleDTO>allScheduleDTOs) {

        ScheduleDTO sch;
        if (allSchedules != null) {
            for (Schedule s : allSchedules) {
                sch = new ScheduleDTO();
                BeanUtils.copyProperties(s, sch);
                sch.setEmployeeIds(s.getEmployeeIds().stream().map(Employee::getId).collect(Collectors.toList()));
                sch.setDate(s.getDate());
                sch.setActivities(s.getActivities());
                List<Long> pets = new ArrayList<>();
                for(Pet p : s.getPetIds()){
                    pets.add(p.getId());
                }
                sch.setPetIds(pets);
                allScheduleDTOs.add(sch);
            }
        }
        return allScheduleDTOs;

    }
}
