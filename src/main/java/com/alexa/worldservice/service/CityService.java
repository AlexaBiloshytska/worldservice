package com.alexa.worldservice.service;

import com.shelberg.entity.City;
import com.shelberg.entity.SearchCity;
import com.shelberg.search.CitySearchQuery;

import java.util.List;

public interface CityService {
    List<SearchCity> getCitiesByCriteria(CitySearchQuery citySearchQuery);

    void add(City city);

    void update(City city);

    void delete(int id);

    City getCityById(int id);
}
