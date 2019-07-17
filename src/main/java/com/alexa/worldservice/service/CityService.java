package com.alexa.worldservice.service;

import com.alexa.worldservice.entity.City;

import java.util.List;

public interface CityService {
    List<City> getCitiesByCriteria(String country, String name, String continent);
}
