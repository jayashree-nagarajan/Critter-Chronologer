package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.Pet;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private PetService petService;
    private CustomerService customerService;

    public PetController(PetService petService, CustomerService customerService) {
        this.petService = petService;
        this.customerService = customerService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = petService.save(petDTOToPetFunction.apply(petDTO));
        Customer customer = pet.getCustomer();
        List<Pet> pets = new ArrayList<>();
        if (customer.getPets() != null) {
            pets.addAll(customer.getPets());
        }
        pets.add(pet);
        customer.setPets(pets);
        customerService.save(customer);
        return petToPetDTOFunction.apply(pet);
    } @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getById(petId);
        return petToPetDTOFunction.apply(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        return petService.getAll().stream().map(petToPetDTOFunction).collect(Collectors.toList());
    }


    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return petService.getByCustomerId(ownerId).stream().map(petToPetDTOFunction).collect(Collectors.toList());
    }

    Function<Pet, PetDTO> petToPetDTOFunction = pet -> {
        PetDTO newPetDTO = new PetDTO();
        BeanUtils.copyProperties(pet, newPetDTO);
        newPetDTO.setOwnerId(pet.getCustomer().getId());
        newPetDTO.setType(pet.getPetType());
        return newPetDTO;
    };

    Function<PetDTO, Pet> petDTOToPetFunction = petDTO -> {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        pet.setCustomer(customerService.getByCustomerId(petDTO.getOwnerId()));
        pet.setPetType(petDTO.getType());
        return pet;
    };
}
