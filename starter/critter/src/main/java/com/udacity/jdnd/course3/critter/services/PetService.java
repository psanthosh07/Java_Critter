package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.PetsData;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {
@Autowired
    private PetRepository petRepository;
@Autowired
    private CustomerRepository customerRepository;

public List<PetsData> getAllPets(){
    return petRepository.findAll();
}
public List<PetsData> getAllPetsByCustomerId(long Id){
    return petRepository.getAllByCustomerId(Id);
}
public PetsData getAllPetsById(long Id){
    return petRepository.getReferenceById(Id);
}

    public PetsData savePet(PetsData pet, long ownerId) {
        Customer customer = customerRepository.getReferenceById(ownerId);
        pet.setCustomer(customer);
        pet =petRepository.save(pet);
        customer.save(pet);
        customerRepository.save(customer);
        return pet;
    }
}
