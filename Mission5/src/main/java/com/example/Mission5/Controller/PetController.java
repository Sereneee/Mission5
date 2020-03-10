package com.example.Mission5.Controller;

import com.example.Mission5.Model.Pet;
import com.example.Mission5.Service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@EnableAutoConfiguration

@RestController // specialized version of controller (@Controller + @ResponseBody)
// @Controller is used to mark a class as a web request handler
@RequestMapping("/Pet")

public class PetController {
    @Autowired
    PetService petService; // creating an instance of PetService so that its functions can be used

    /* create pet */
    @PostMapping
    public int create(@RequestBody Pet pet) {
        return petService.createPet(pet);
    }

    /* read all pet */
    @GetMapping
    //@ResponseBody  - no need this when there is @RestController written on top
    public List<Pet> readAllPets() {
        return petService.readAll();
    }

    /* read specific pet by id */
    @GetMapping("/{id}")
    public Pet readById(@PathVariable(value = "id") String iden) {
        return petService.readById(iden);
    }

    /* read specific pet by name and age */
    @GetMapping("/{name}/{age}")
    public List<Pet> readByIdAndName(@PathVariable(value = "name") String nm, @PathVariable(value = "age") String age) {
        return petService.readByNameAndAge(nm, age);
    }

    /* count how many pets in the pet table */
    @GetMapping("/count")
    public int countPet() throws Exception {
        return petService.countPet();
    }

    /* update pet */
    @PutMapping("/{id}")
    public int updatePet(@PathVariable(value = "id") String iden, @RequestBody Pet tempPet) throws SQLException {
        return petService.updatePet(iden, tempPet);
    }

    /* delete pet */
    @DeleteMapping("/{id}")
    public int deletePet(@PathVariable(value = "id") String iden) throws SQLException {
        return petService.deletePet(iden);
    }
}
