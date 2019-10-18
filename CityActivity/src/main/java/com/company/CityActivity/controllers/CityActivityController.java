package com.company.CityActivity.controllers;

import com.company.CityActivity.exceptions.NotFoundException;
import com.company.CityActivity.models.City;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CityActivityController {



    private List <City> cityList;

    private static int idCount = 1;

    public CityActivityController() {
        cityList = new ArrayList<>();

        cityList.add(new City("New York City", "New York", 2_000_000,false,idCount++));
        cityList.add(new City("Miami", "Florida", 400_000,false,idCount++));
        cityList.add(new City("Trenton", "New Jersey", 28_000,true,idCount++));
        cityList.add(new City("Los Angeles", "California", 1_000_000,false,idCount++));
    }

    @GetMapping(value = "/city")
    @ResponseStatus(value= HttpStatus.OK)
    public List<City> getAllCities(){
        return cityList;
    }

    @GetMapping(value="/city/{name}")
    @ResponseStatus(value = HttpStatus.OK)
    public City getCityByName(@PathVariable String name){
        City foundCity = null;

        for(City cName : cityList){
            if(cName.getName().equals(name)){
                foundCity = cName;
                break;
            }
        }

        if(foundCity == null){
            throw new NotFoundException("City not found");
        }

        return foundCity;
    }


    @PostMapping(value = "/city")
    @ResponseStatus(value = HttpStatus.CREATED)
    public City postCity(@RequestBody @Valid City city){
        city.setId(idCount++);
        cityList.add(city);

        return city;
    }

    @DeleteMapping(value="/city/{name}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCity(@PathVariable String name){

        City foundCity = null;

        for(City cName : cityList){
            if(cName.getName().equals(name)){
                foundCity = cName;
                break;
            }
        }

        if(foundCity == null){
            throw new NotFoundException("City not found");
        }

        cityList.remove(cityList.indexOf(foundCity));



    }




}
