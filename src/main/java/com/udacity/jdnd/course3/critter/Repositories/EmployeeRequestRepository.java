package com.udacity.jdnd.course3.critter.Repositories;

import com.udacity.jdnd.course3.critter.user.EmployeeRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRequestRepository extends JpaRepository<EmployeeRequest,Long> {
}
