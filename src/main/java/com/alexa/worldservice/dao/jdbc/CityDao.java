package com.alexa.worldservice.dao.jdbc;

import com.shelberg.entity.City;
import com.shelberg.entity.SearchCity;
import com.shelberg.search.CitySearchQuery;

import java.util.List;

public interface CityDao {

    City add(City city);

    City update(City city);

    void delete(int id);

    City getCityById(int id);
    List<SearchCity> searchCityByCriteria(CitySearchQuery CitySearchQuery);
}
