package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.PetsData;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PetRepository petRepository;

    // Fetch all customers
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Fetch customer by pet ID
    public Customer getCustomerByPetId(long petId) {
        Optional<PetsData> petOptional = petRepository.findById(petId);
        if (petOptional.isPresent()) {
            return petOptional.get().getCustomer();
        }
        throw new RuntimeException("Pet not found with ID: " + petId);
    }

    // Save customer along with their pets
    public Customer saveCustomer(Customer customer, List<Long> petIds) {
        List<PetsData> pets = getPetsByIds(petIds);
        customer.setLi(pets);
        return customerRepository.save(customer);
    }

    // Fetch a list of pets by their IDs
    private List<PetsData> getPetsByIds(List<Long> petIds) {
        if (petIds == null || petIds.isEmpty()) {
            return new ArrayList<>();
        }
        return petIds.stream()
                .map(petId -> petRepository.findById(petId)
                        .orElseThrow(() -> new RuntimeException("Pet not found with ID: " + petId)))
                .collect(Collectors.toList());
    }

    // Utility method to check if a customer has a pet
    public boolean doesCustomerHavePet(Customer customer, long petId) {
        return customer.getLi().stream().anyMatch(pet -> pet.getId() == petId);
    }

    // Utility method to add a pet to a customer
    public void addPetToCustomer(Customer customer, long petId) {
        PetsData pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found with ID: " + petId));
        customer.getLi().add(pet);
        customerRepository.save(customer);
    }
}
