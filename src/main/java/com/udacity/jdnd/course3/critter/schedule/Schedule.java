package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "SCHEDULE")
public class Schedule {
    @Id
    @GeneratedValue
    private long id;

    @ManyToMany(targetEntity = Employee.class)
    @Column(name="SCHEDULE_EMPLOYEEIDS")
    private List<Employee> employeeIds;
    @Column(name="SCHEDULE_PETIDS")
    @ManyToMany(targetEntity = Pet.class)
    private List<Pet> petIds;

    private LocalDate date;

    @ElementCollection
    @Column(name="SCHEDULE_ACTIVITIES")
    private Set<EmployeeSkill> activities;

    public Schedule(List<Employee> employeeIds, List<Pet> petIds, LocalDate date, Set<EmployeeSkill> activities) {

        this.employeeIds = employeeIds;
        this.petIds = petIds;
        this.date = date;
        this.activities = activities;
    }

    public Schedule() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Employee> getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(List<Employee> employeeIds) {
        this.employeeIds = employeeIds;
    }

    public List<Pet> getPetIds() {
        return petIds;
    }

    public void setPetIds(List<Pet> petIds) {
        this.petIds = petIds;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }
}
