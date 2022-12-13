package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.Pet;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PetService {
    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet save(Pet pet) {
        return petRepository.save(pet);
    }

    public Pet getById(long petId) {
        return petRepository.getOne(petId);
    }

    public List<Pet> getAll() {
        return petRepository.findAll();
    }

    public List<Pet> getByCustomerId(long ownerId) {
        return petRepository.findAllByCustomerId(ownerId);
    }
}
