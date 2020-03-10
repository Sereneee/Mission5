package com.example.Mission5.Service;

import com.example.Mission5.Model.Pet;
import com.example.Mission5.Repository.PetRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest

public class PetServiceTest {
    @InjectMocks
    private PetService petService;

    @Mock
    private PetRepo petRepo;

    // declaration and instantiation of a variable that is used to signify successful C/U/D on db - 1 means success, 0 means fail
    public static int oneInt = 1;
    public static String oneStr = "1";

    // test create in service
    @Test
    public void createSvcTest() {
        Pet tempPet = new Pet("100", "apples", 1, "dog");
        when(petRepo.createPet(tempPet)).thenReturn(oneInt);
        int result = petRepo.createPet(tempPet);
        assertEquals(oneInt, result);
    }

    // test read all in service
    @Test
    public void readAllSvcTest() {
        List<Pet> tempPetList = new ArrayList<>();
        Pet pet1 = new Pet("100", "apples", 1, "dog");
        Pet pet2 = new Pet("101", "bridget", 2, "cat");
        tempPetList.add(pet1);
        tempPetList.add(pet2);

        when(petRepo.readAll()).thenReturn(tempPetList);
        List<Pet> resultPetList = petService.readAll();

        assertEquals(tempPetList.get(0).getId(), resultPetList.get(0).getId());
        assertEquals(tempPetList.get(0).getName(), resultPetList.get(0).getName());
        assertEquals(tempPetList.get(0).getAge(), resultPetList.get(0).getAge());
        assertEquals(tempPetList.get(0).getType(), resultPetList.get(0).getType());
        assertEquals(tempPetList.get(1).getId(), resultPetList.get(1).getId());
        assertEquals(tempPetList.get(1).getName(), resultPetList.get(1).getName());
        assertEquals(tempPetList.get(1).getAge(), resultPetList.get(1).getAge());
        assertEquals(tempPetList.get(1).getType(), resultPetList.get(1).getType());
    }

    // test read by id in service
    @Test
    public void readByIdSvcTest() {
        String id = "100", name = "apples", type = "dog";
        int age = 1;
        Pet tempPet = new Pet(id, name, age, type);
        when(petRepo.readById(id)).thenReturn(tempPet);
        Pet result = petService.readById(id);

        assertEquals(id, result.getId());
        assertEquals(name, result.getName());
        assertEquals(age, result.getAge());
        assertEquals(type, result.getType());
    }

    // test read by name and age in service
    @Test
    public void readByNameAndAgeSvcTest() {
        String name = "apples";
        int age = 1;
        List<Pet> tempPetList = new ArrayList<>();
        Pet pet1 = new Pet("100", name, age, "dog");
        Pet pet2 = new Pet("101", name, age, "cat");
        tempPetList.add(pet1);
        tempPetList.add(pet2);

        when(petRepo.readByNameAndAge(name, Integer.toString(age))).thenReturn(tempPetList);
        List<Pet> resultPetList = petService.readByNameAndAge(name, Integer.toString(age));

        assertEquals(tempPetList.get(0).getId(), resultPetList.get(0).getId());
        assertEquals(tempPetList.get(0).getName(), resultPetList.get(0).getName());
        assertEquals(tempPetList.get(0).getAge(), resultPetList.get(0).getAge());
        assertEquals(tempPetList.get(0).getType(), resultPetList.get(0).getType());
        assertEquals(tempPetList.get(0).getId(), resultPetList.get(0).getId());
        assertEquals(tempPetList.get(1).getId(), resultPetList.get(1).getId());
        assertEquals(tempPetList.get(1).getName(), resultPetList.get(1).getName());
        assertEquals(tempPetList.get(1).getAge(), resultPetList.get(1).getAge());
        assertEquals(tempPetList.get(1).getType(), resultPetList.get(1).getType());
    }

    // test read number of pet in db in service
    @Test
    public void countPetSvcTest() throws Exception {
        List<Pet> localPetList = new ArrayList<>();
        Pet pet1 = new Pet("100", "apples", 1, "dog");
        Pet pet2 = new Pet("101", "bridget", 2, "cat");

        localPetList.add(pet1);
        localPetList.add(pet2);

        when(petRepo.countPet()).thenReturn(localPetList.size());

        int result = petService.countPet();

        assertEquals(localPetList.size(), result);
    }

    // test update pet in service
    @Test
    public void updatePetSvcTest() throws SQLException {
        String id = "100";
        Pet pet = new Pet(id, "apples", 1, "dog");
        when(petRepo.updatePet(id, pet)).thenReturn(oneInt);
        int result = petService.updatePet(id, pet);
        assertEquals(oneInt, result);
    }

    // test delete pet in service
    @Test
    public void deletePetSvcTest() throws SQLException {
        String id = "100";
        when(petRepo.deletePet(id)).thenReturn(oneInt);
        int result = petService.deletePet(id);
        assertEquals(oneInt, result);
    }
}
