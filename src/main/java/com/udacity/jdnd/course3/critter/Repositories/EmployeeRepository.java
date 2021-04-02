package com.udacity.jdnd.course3.critter.Repositories;

import com.udacity.jdnd.course3.critter.Entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
