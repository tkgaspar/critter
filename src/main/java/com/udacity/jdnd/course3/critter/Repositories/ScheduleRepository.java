package com.udacity.jdnd.course3.critter.Repositories;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    public List<Schedule> findByPetIds_Id(Long petId);

    public List<Schedule> findByEmployeeIds_Id(Long employeeId);

    public List<Schedule> findByCustomerIds_Id(Long customerId);

}
