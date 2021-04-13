package com.udacity.jdnd.course3.critter.Service;

import com.udacity.jdnd.course3.critter.Repositories.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.Repositories.ScheduleRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    CustomerRepository customerRepository;

    public Long saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule).getId();
    }

    public Schedule findById(Long id) {
        Optional<Schedule> schedule = scheduleRepository.findById(id);
        if (schedule.isPresent()) {
            return schedule.get();
        } else {
            throw new ObjectNotFoundException(300, "Schedule with id: " + id + " was not found!");
        }
    }

    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> findByPet(Pet pet) {
        return scheduleRepository.findByPetIds_Id(pet.getId());
    }

    public List<Schedule> findByPetId(Long petId) {
        return scheduleRepository.findByPetIds_Id(petId);
    }

    public List<Schedule> findByEmployee(Employee e) {
        return scheduleRepository.findByEmployeeIds_Id(e.getId());
    }

    public List<Schedule> findByEmployeeId(Long employeeId) {
        return scheduleRepository.findByEmployeeIds_Id(employeeId);
    }

    public List<Schedule> getScheduleForCustomer(Long customerId) {
        Optional<Customer> optionalUser = customerRepository.findById(customerId);
        Customer customer = optionalUser.orElse(null);
        List<Schedule> schedules = new ArrayList<>();
        List<Pet> pets = Objects.requireNonNull(customer).getPets();
        for (Pet p : pets) {
            schedules.addAll(scheduleRepository.findByPetIds_Id(p.getId()));

        }
        return schedules;
    }

    public List<Schedule> getSCheduleforPet(Long petId) {
        return scheduleRepository.findByPetIds_Id(petId);
    }
}
