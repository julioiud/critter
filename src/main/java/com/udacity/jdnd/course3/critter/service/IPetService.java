package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;

import java.util.List;

public interface IPetService {

    Pet createPet(PetDTO petDTO);

    Pet getPetByID(Long id);

    List<Pet> getAllPets();

    List<Pet> getPetsByOwnerId(Long ownerId);
}
