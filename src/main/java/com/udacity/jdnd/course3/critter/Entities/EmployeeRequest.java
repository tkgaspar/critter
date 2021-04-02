package com.udacity.jdnd.course3.critter.Entities;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class EmployeeRequest {
    @Id
    @GeneratedValue
    private Long id;
    @ElementCollection
    private Set<EmployeeSkill> skills;

    private LocalDate date;

    public EmployeeRequest(Long id, Set<EmployeeSkill> skills, LocalDate date) {
        this.id = id;
        this.skills = skills;
        this.date = date;
    }

    public EmployeeRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
