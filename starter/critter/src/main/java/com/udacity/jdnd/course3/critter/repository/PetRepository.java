package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.user.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findAllByCustomerId(long id);
}
