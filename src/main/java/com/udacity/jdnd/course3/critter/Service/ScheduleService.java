package com.udacity.jdnd.course3.critter.Service;

import com.udacity.jdnd.course3.critter.Entities.Customer;
import com.udacity.jdnd.course3.critter.Entities.Employee;
import com.udacity.jdnd.course3.critter.Entities.Pet;
import com.udacity.jdnd.course3.critter.Entities.Schedule;
import com.udacity.jdnd.course3.critter.Repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    public Long saveSchedule(Schedule schedule){
        return scheduleRepository.save(schedule).getId();
    }

    public Schedule findById(Long id){
        return scheduleRepository.findById(id).get();
    }

    public List<Schedule> findAll(){
        return scheduleRepository.findAll();
    }

    public List<Schedule> findByPet(Pet pet){
        return scheduleRepository.findByPetIds(pet.getId());
    }
    public List<Schedule> findByEmployee(Employee e){
        return scheduleRepository.findByEmployeeIds(e.getId());
    }
    public List<Schedule> findByCustomer(Customer c){
        return scheduleRepository.findByCustomerIds(c.getId());
    }

}
