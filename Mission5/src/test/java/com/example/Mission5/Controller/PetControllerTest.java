package com.example.Mission5.Controller;

import com.example.Mission5.Model.Pet;
import com.example.Mission5.Service.PetService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
//@WebMvcTest(PetController.class) //it will autoconfigure the spring mvc infrastructure for our unit tests
public class PetControllerTest {

    // declaration and instantiation of a variable that is used to signify successful C/U/D on db
    public static int oneInt = 1;
    public static String oneStr = "1";

    private MockMvc mockMvc;

    @InjectMocks
    private PetController petCtrl;

    @Mock
    private PetService petService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(petCtrl)
                .build();
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // test creation of pet in controller
    @Test
    public void createCtrlerTest() throws Exception {
        Pet tempPet = new Pet("100", "apples", 1, "dog");

        when(petService.createPet(tempPet)).thenReturn(oneInt);

        mockMvc.perform(post("/Pet")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(asJsonString(tempPet)))
                .andDo(print())
                .andExpect(status().isOk());
//                .andExpect(content().string(coSntainsString(oneStr)));

        int result = petCtrl.create(tempPet);
        assertEquals(oneInt, result); // test if it return as what was expected
        verify(petService).createPet(tempPet); // test if method is being invoked
    }

    // test read all pet in controller
    @Test
    public void readAllCtrlerTest() throws Exception {

        List<Pet>localPetList = new ArrayList<>();
        Pet pet1 = new Pet("100", "apples", 1, "dog" );
        Pet pet2 = new Pet("101", "bridget", 2, "cat" );

        localPetList.add(pet1);
        localPetList.add(pet2);

        when(petService.readAll()).thenReturn(localPetList);

        mockMvc.perform(get("/Pet")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(localPetList.size())))
                .andExpect(jsonPath("$[0].id", is(localPetList.get(0).getId())))
                .andExpect(jsonPath("$[0].name", is(localPetList.get(0).getName())))
                .andExpect(jsonPath("$[0].age", is(localPetList.get(0).getAge())))
                .andExpect(jsonPath("$[0].type", is(localPetList.get(0).getType())))
                .andExpect(jsonPath("$[1].id", is(localPetList.get(1).getId())))
                .andExpect(jsonPath("$[1].name", is(localPetList.get(1).getName())))
                .andExpect(jsonPath("$[1].age", is(localPetList.get(1).getAge())))
                .andExpect(jsonPath("$[1].type", is(localPetList.get(1).getType())));
        verify(petService).readAll();
    }

    // test read by id in controller
    @Test
    public void readByIdCtlerTest() throws Exception {
        String id = "100";
        Pet testPet = new Pet(id, "apples", 1, "dog");

        when(petService.readById(id)).thenReturn(testPet);

        mockMvc.perform(get("/Pet/{id}", "100")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(id)))
                .andExpect(jsonPath("$.name", Matchers.is("apples")))
                .andExpect(jsonPath("$.age", Matchers.is(1)))
                .andExpect(jsonPath("$.type", Matchers.is("dog")));

        verify(petService).readById(id);
    }

    // test read by name and age in controller
    @Test
    public void readByNameAndAgeCtrlerTest() throws Exception{
        String name = "apples";
        int age = 1;
        List<Pet>localPetList = new ArrayList<>();
        Pet pet1 = new Pet("100", name, 1, "dog" );
        Pet pet2 = new Pet("101", name, 1, "cat" );

        localPetList.add(pet1);
        localPetList.add(pet2);

        when(petService.readByNameAndAge(name, Integer.toString(age))).thenReturn(localPetList);

        mockMvc.perform(get("/Pet/{name}/{age}", name, age)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(localPetList.size())))
                .andExpect(jsonPath("$[0].id", is(localPetList.get(0).getId())))
                .andExpect(jsonPath("$[0].name", is(localPetList.get(0).getName())))
                .andExpect(jsonPath("$[0].age", is(localPetList.get(0).getAge())))
                .andExpect(jsonPath("$[0].type", is(localPetList.get(0).getType())))
                .andExpect(jsonPath("$[1].id", is(localPetList.get(1).getId())))
                .andExpect(jsonPath("$[1].name", is(localPetList.get(1).getName())))
                .andExpect(jsonPath("$[1].age", is(localPetList.get(1).getAge())))
                .andExpect(jsonPath("$[1].type", is(localPetList.get(1).getType())));
        verify(petService).readByNameAndAge(name, Integer.toString(age));
    }

    // method to retrieve size of list <pet> in terms of string - to be used for the method below this
    public static String returnIntFrmList(List<Pet>petList)
    {
        return Integer.toString(petList.size());
    }

    // test read number of pet in db in controller
    @Test
    public void countPetCtrlerTest() throws Exception{
        List<Pet>localPetList = new ArrayList<>();
        Pet pet1 = new Pet("100", "apples", 1, "dog" );
        Pet pet2 = new Pet("101", "bridget", 2, "cat" );

        localPetList.add(pet1);
        localPetList.add(pet2);

        when(petService.countPet()).thenReturn(localPetList.size());

        mockMvc.perform(get("/Pet/count")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(Integer.toString(localPetList.size())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(Integer.toString(localPetList.size()))));

        int result = petCtrl.countPet();
        assertEquals(localPetList.size(), result);
    }

    // test update pet in controller
    @Test
    public void updatePetCtrlerTest() throws Exception{
        String id = "100";
        Pet tempPet = new Pet(id, "apples", 1, "dog");
        when (petService.updatePet(id,tempPet)).thenReturn(oneInt);

        mockMvc.perform(put("/Pet/{id}",id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(asJsonString(tempPet)))
                .andDo(print())
                .andExpect(status().isOk());
//                .andExpect(content().string(containsString(oneStr)));

        int result = petCtrl.updatePet(id, tempPet);
        assertEquals(oneInt, result);
        verify(petService).updatePet(id, tempPet);
    }

    // test delete pet in controller
    @Test
    public void deletePetCtrlerTest() throws Exception{
        String id = "101";
        when(petService.deletePet(id)).thenReturn(oneInt);

        mockMvc.perform( MockMvcRequestBuilders.delete("/Pet/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString(oneStr)));
        int result = petCtrl.deletePet(id);

        assertEquals(oneInt, result);

//        verify(petService).deletePet(id);
    }
}
