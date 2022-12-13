package com.udacity.jdnd.course3.critter.user;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue
    private Long id;
    @Column(length = 128)
    @Nationalized
    private String name;
    @Column(length = 50)
    private String phoneNumber;
    @Column(length = 5000)
    @Nationalized
    private String notes;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.PERSIST)
    private List<Pet> pets;
}
