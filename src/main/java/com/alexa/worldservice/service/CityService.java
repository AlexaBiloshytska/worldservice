package com.alexa.worldservice.service;

import com.shelberg.entity.City;
import com.shelberg.entity.SearchCity;
import com.shelberg.search.CitySearchQuery;

import java.util.List;

public interface CityService {
    List<SearchCity> getCitiesByCriteria(CitySearchQuery citySearchQuery);

    City add(City city);

    City update(City city);

    void delete(int id);

    City getCityById(int id);
}
