package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.service.IPetService;
import com.udacity.jdnd.course3.critter.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private IPetService petService;
    private final UserUtil userUtil;

    @Autowired
    public PetController(IPetService petService,
                         UserUtil userUtil){
        this.petService = petService;
        this.userUtil = userUtil;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = petService.createPet(petDTO);
        return userUtil.convertPetToPetDTO(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getPetByID(petId);
        return userUtil.convertPetToPetDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.getAllPets();
        return userUtil.convertPetsToPetsDTO(pets);
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets = petService.getPetsByOwnerId(ownerId);
        return userUtil.convertPetsToPetsDTO(pets);
    }
}
