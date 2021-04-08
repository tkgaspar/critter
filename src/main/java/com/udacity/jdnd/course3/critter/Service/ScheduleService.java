package com.udacity.jdnd.course3.critter.Service;

import com.udacity.jdnd.course3.critter.Entities.Customer;
import com.udacity.jdnd.course3.critter.Entities.Employee;
import com.udacity.jdnd.course3.critter.Entities.Pet;
import com.udacity.jdnd.course3.critter.Entities.Schedule;
import com.udacity.jdnd.course3.critter.Repositories.ScheduleRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    public Long saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule).getId();
    }

    public Schedule findById(Long id) {
        Optional <Schedule> schedule = scheduleRepository.findById(id);
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

    public List<Schedule> findByEmployee(Employee e) {
        return scheduleRepository.findByEmployeeIds_Id(e.getId());
    }

    public List<Schedule> findByCustomer(Customer c) {
        return scheduleRepository.findByCustomerIds_Id(c.getId());
    }

    public List<Schedule> getSCheduleforPet(Long petId) {
        return scheduleRepository.findByPetIds_Id(petId);
    }
}
