package com.company.CityActivity.controllers;


import com.company.CityActivity.models.City;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CityActivityController.class)
public class CityActivityControllerTests {

    @Autowired
    private MockMvc mc;

    private ObjectMapper mapper = new ObjectMapper();

    private List<City> cityList;


    @Before
    public void setUp() throws Exception {
        cityList = new ArrayList<>();

        int idCount = 1;

        cityList.add(new City("New York City", "New York", 2_000_000,false,idCount++));
        cityList.add(new City("Miami", "Florida", 400_000,false,idCount++));
        cityList.add(new City("Trenton", "New Jersey", 28_000,true,idCount++));
        cityList.add(new City("Los Angeles", "California", 1_000_000,false,idCount++));

    }




    @Test
    public void shouldReturnAllCities() throws Exception {

        String outputJson = mapper.writeValueAsString(cityList);

        mc.perform(get("/city"))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(content().json(outputJson));

    }


    @Test
    public void shouldAddNewCity () throws Exception {
        City inputCity = new City();

        inputCity.setName("Boston");
        inputCity.setState("Massachusetts");
        inputCity.setPopulation(500_000);
        inputCity.setCapital(false);

        String inputJson = mapper.writeValueAsString(inputCity);


        City outputCity = new City();
        outputCity.setName("Boston");
        outputCity.setState("Massachusetts");
        outputCity.setPopulation(500_000);
        outputCity.setCapital(false);
        outputCity.setId(5);

        String outputJson = mapper.writeValueAsString(outputCity);


        mc.perform(post("/city")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }



   @Test
   public void shouldRetrieveACityByName() throws Exception{
       City outputCity = new City();
       outputCity.setName("New York City");
       outputCity.setState("New York");
       outputCity.setPopulation(2_000_000);
       outputCity.setCapital(false);
       outputCity.setId(1);

       String outputJson = mapper.writeValueAsString(outputCity);

       mc.perform(get("/city/New York City"))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(content().json(outputJson));

   }


    @Test
    public void shouldDeleteACityByName() throws Exception {


        mc.perform(delete("/city/New York City"))
                .andDo(print())
                .andExpect(status().isNoContent());

    }






}
