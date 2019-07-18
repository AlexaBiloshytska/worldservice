package com.alexa.worldservice.service;

import com.alexa.worldservice.dao.jdbc.CityDao;
import com.alexa.worldservice.entity.SearchCity;

import java.util.List;

public class DefaultCityService implements CityService {
    private CityDao cityDao;

    public DefaultCityService(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    @Override
    public List<SearchCity> getCitiesByCriteria(Boolean country, Boolean name, Boolean continent) {
        return cityDao.searchCityByCriteria(country,name,continent);
    }
}
