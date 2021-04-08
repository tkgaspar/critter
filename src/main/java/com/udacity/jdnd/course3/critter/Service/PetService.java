package com.udacity.jdnd.course3.critter.Service;


import com.udacity.jdnd.course3.critter.Entities.Customer;
import com.udacity.jdnd.course3.critter.Entities.Pet;
import com.udacity.jdnd.course3.critter.Repositories.CustomerRepository;
import com.udacity.jdnd.course3.critter.Repositories.PetRepository;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetService {
    @Autowired
    PetRepository petRepository;
    @Autowired
    CustomerRepository customerRepository;


    public Pet savePet(Pet pet) {
        Pet savedPet=petRepository.save(pet);
        Customer customer=savedPet.getOwner();
        customer.addPet(savedPet);
        customerRepository.save(customer);
        return savedPet;
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

    public List<Pet>findAll(){
        return petRepository.findAll();
    }

    public List<Pet>getPetsByOwnerId(Long ownerId){
        Customer customer =customerRepository.getOne(ownerId);
        return customer.getPets();
    }
    public Pet findById(long petId){
        Optional<Pet> pet = petRepository.findById(petId);
        if(pet.isPresent()){
            return pet.get();
        } else {
            throw new ObjectNotFoundException(300,"Pet with id: "+ petId + " not found");
        }
    }

}
