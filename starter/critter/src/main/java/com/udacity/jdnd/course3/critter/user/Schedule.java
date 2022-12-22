package com.udacity.jdnd.course3.critter.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
@Entity
@Getter
@Setter
public class Schedule {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany(targetEntity = Employee.class)
    private List<Employee> employee;

    @ManyToMany(targetEntity = Pet.class)
    private List<Pet> pet;

    private LocalDate date;

    @ElementCollection
    private Set<EmployeeSkill> activities;
}
