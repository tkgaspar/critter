package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.Entities.Customer;
import com.udacity.jdnd.course3.critter.Entities.Pet;
import com.udacity.jdnd.course3.critter.Service.CustomerService;
import com.udacity.jdnd.course3.critter.Service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;
    @Autowired
    CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = convertPetDTOToPet(petDTO);
        Pet savedPet = petService.savePet(pet);
        return convertPetToPetDTO(savedPet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        PetDTO petDTO = convertPetToPetDTO(petService.findById(petId));
        return petDTO;
    }

    @GetMapping("/all")
    public List<PetDTO> getPets() {
        List<Pet> allPets = petService.findAll();
        List<PetDTO> petDTOList = new ArrayList<>();
        allPets.forEach(i -> {
            PetDTO dto = convertPetToPetDTO(i);
            petDTOList.add(dto);
        });
        return petDTOList;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets = petService.getPetsByOwnerId(ownerId);
        List<PetDTO> petDTOs = new ArrayList<>();
       for(Pet pet:pets){
           petDTOs.add(convertPetToPetDTO(pet));
       }
        return petDTOs;
    }

    private PetDTO convertPetToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO,"owner");
        petDTO.setOwnerId(pet.getOwner().getId());
        return petDTO;
    }

    private Pet convertPetDTOToPet(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet,"ownerId");
        Customer customer = customerService.getOne(petDTO.getOwnerId());
        pet.setOwner(customer);
        return pet;
    }
}
