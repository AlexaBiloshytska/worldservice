package com.alexa.worldservice.dao.jdbc;

import com.alexa.worldservice.entity.City;

import java.util.List;

public interface CityDao {
    List<City> searchCityByCriteria(String country, String name, String continent);
}
