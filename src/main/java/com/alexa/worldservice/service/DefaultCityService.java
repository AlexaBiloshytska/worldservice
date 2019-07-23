package com.alexa.worldservice.service;

import com.alexa.worldservice.dao.jdbc.CityDao;
import com.alexa.worldservice.entity.CitySearchCriteria;
import com.shelberg.entity.SearchCity;

import java.util.List;

public class DefaultCityService implements CityService {
    private CityDao cityDao;

    public DefaultCityService(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    @Override
    public List<SearchCity> getCitiesByCriteria(CitySearchCriteria citySearchCriteria) {
        return cityDao.searchCityByCriteria(citySearchCriteria);
    }
}
