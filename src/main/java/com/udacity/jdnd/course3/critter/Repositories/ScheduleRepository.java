package com.udacity.jdnd.course3.critter.Repositories;
import com.udacity.jdnd.course3.critter.Entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    public List<Schedule> findByPetIds(Long petId);

    public List<Schedule> findByEmployeeIds(Long employeeId);

    public List<Schedule> findByCustomerIds(Long customerId);

}
