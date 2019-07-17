package com.alexa.worldservice.service;

import com.alexa.worldservice.dao.jdbc.CityDao;
import com.alexa.worldservice.entity.City;

import java.util.List;

public class DefaultCityService implements CityService {
    private CityDao cityDao;
    @Override
    public List<City> getCitiesByCriteria(String country, String name, String continent) {
        return cityDao.searchCityByCriteria(country,name,continent);
    }
}
