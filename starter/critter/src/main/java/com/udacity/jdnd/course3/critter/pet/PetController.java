package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.entity.PetsData;
import com.udacity.jdnd.course3.critter.services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {

        PetsData pet=new PetsData();
        pet.setPetType(petDTO.getType());
        pet.setBirth(petDTO.getBirthDate());
        pet.setName(petDTO.getName());
        pet.setNotes(petDTO.getNotes());
        return getPetDTO(petService.savePet(pet,petDTO.getOwnerId()));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return getPetDTO(petService.getAllPetsById(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<PetsData> pets = petService.getAllPets();
        return pets.stream().map(this::getPetDTO).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<PetsData> pet=petService.getAllPetsByCustomerId(ownerId);
        return pet.stream().map(this::getPetDTO).collect(Collectors.toList());
    }
    private PetDTO getPetDTO(PetsData pet) {
        PetDTO petDTO = new PetDTO();
        petDTO.setId(pet.getId());
        petDTO.setName(pet.getName());
        petDTO.setType(pet.getPetType());
        petDTO.setOwnerId(pet.getCustomer().getId());
        petDTO.setBirthDate(pet.getBirth());
        petDTO.setNotes(pet.getNotes());
        return petDTO;
    }
}
