package com.udacity.jdnd.course3.critter.Service;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.Repositories.CustomerRepository;
import com.udacity.jdnd.course3.critter.Repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    PetRepository petRepository;

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id).get();
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer findByPet(Long petId){
        Customer customer=customerRepository.findByPetsId(petId);
        return customer;

    }

    public void savePetForCustomer(Long id, Pet pet){
        Customer c =customerRepository.findById(id).get();
        pet.setOwner(c);
        c.getPets().add(pet);
    }

    public Customer getOne(Long id){
        return customerRepository.getOne(id);
    }
}
