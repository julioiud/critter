package com.udacity.jdnd.course3.critter.service.impl;

import com.udacity.jdnd.course3.critter.data.CustomerRepository;
import com.udacity.jdnd.course3.critter.data.PetRepository;
import com.udacity.jdnd.course3.critter.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.service.IPetService;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetServiceImpl implements IPetService {

    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;
    private final UserUtil userUtil;

    @Autowired
    public PetServiceImpl(PetRepository petRepository,
                          CustomerRepository customerRepository,
                          UserUtil userUtil){
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
        this.userUtil = userUtil;
    }

    @Override
    public Pet createPet(PetDTO petDTO) {
        Optional<Customer> customerDB = customerRepository.findById(petDTO.getOwnerId());
        if(!customerDB.isPresent()){
            throw new PetNotFoundException();
        }
        Pet pet = userUtil.convertPetDTOToPet(petDTO);
        pet.setCustomer(customerDB.get());
        return petRepository.save(pet);
    }

    @Override
    public Pet getPetByID(Long id) {
        return petRepository.findById(id).orElse(null);
    }

    @Override
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @Override
    public List<Pet> getPetsByOwnerId(Long ownerId) {
        return petRepository.findByCustomerId(ownerId);
    }
}
