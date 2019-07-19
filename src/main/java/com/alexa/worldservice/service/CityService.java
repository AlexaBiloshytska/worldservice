package com.alexa.worldservice.service;

import com.alexa.worldservice.entity.SearchCity;

import java.util.List;

public interface CityService {
    List<SearchCity> getCitiesByCriteria(boolean countryRequired, boolean populationRequired,
                                         boolean countryPopulationRequired,
                                         String country,String name, String continent);
}
