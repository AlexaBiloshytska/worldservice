package com.alexa.worldservice.dao.jdbc;

import com.alexa.worldservice.entity.City;
import com.alexa.worldservice.entity.SearchCity;

import java.util.List;

public interface CityDao {
    List<SearchCity> searchCityByCriteria(boolean countryRequired,
                                          boolean populationRequired, boolean countryPopulationRequired,
                                          String country, String name, String continent);

    void add(City city);

    void update(City city);

    void delete(int id);

    City getCityById(int id);
}
