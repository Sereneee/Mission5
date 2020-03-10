package com.example.Mission5.Service;

import com.example.Mission5.Model.Pet;
import com.example.Mission5.Repository.PetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service // indicates its a service class (write business logic in different layers)
public class PetService {
    @Autowired // marks constructor field, setter field to be autowired by spring dependency injection
    private PetRepo petRepo;

    // create pet
    public int createPet(Pet pet) {
        return petRepo.createPet(pet);
    }

    // read all pet
    public List<Pet> readAll() {
        return petRepo.readAll();
    }

    // read specific pet by id
    public Pet readById(String id) {
        return petRepo.readById(id);
    }

    // read specific pet by name and age
    public List<Pet> readByNameAndAge(String name, String age) {
        return petRepo.readByNameAndAge(name, age);
    }

    // count how many pets in the pet table
    public int countPet() throws Exception {
        return petRepo.countPet();
    }

    // update pet
    public int updatePet(String iden, Pet pet) throws SQLException {
        return petRepo.updatePet(iden, pet);
    }

    // delete pet
    public int deletePet(String iden) throws SQLException {
        return petRepo.deletePet(iden);
    }
}
