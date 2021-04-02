package com.udacity.jdnd.course3.critter.Service;


import com.udacity.jdnd.course3.critter.Entities.Customer;
import com.udacity.jdnd.course3.critter.Entities.Pet;
import com.udacity.jdnd.course3.critter.Repositories.CustomerRepository;
import com.udacity.jdnd.course3.critter.Repositories.PetRepository;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PetService {
    @Autowired
    PetRepository petRepository;
    @Autowired
    CustomerRepository customerRepository;


    public Pet savePet(Pet pet) {
        return petRepository.save(pet);
    }


    public Pet findById(Long id) {
        return petRepository.findById(id).get();
    }

    public Pet saveOwnerForPet(Pet pet) {
        Pet savedPet = petRepository.save(pet);
        Customer owner = savedPet.getOwner();
        owner.addPet(savedPet);
        customerRepository.save(owner);
        return savedPet;
    }

    public List<Pet> getAllPetsByIds(List<Long> ids) {
        return petRepository.findAllById(ids);
    }

}
