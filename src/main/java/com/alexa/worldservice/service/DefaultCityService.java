package com.alexa.worldservice.service;

import com.alexa.worldservice.dao.jdbc.CityDao;
import com.alexa.worldservice.entity.City;
import com.alexa.worldservice.entity.SearchCity;

import java.util.List;

public class DefaultCityService implements CityService {
    private CityDao cityDao;

    public DefaultCityService(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    @Override
    public List<SearchCity> getCitiesByCriteria(boolean countryReqired, boolean populationRequired, boolean countryPopulationRequired, String
                                                country, String name, String continent) {
        return cityDao.searchCityByCriteria(countryReqired, populationRequired, countryPopulationRequired, country,name, continent);
    }

    @Override
    public void add(City city) {
        cityDao.add(city);
    }

    @Override
    public void update(City city) {
        cityDao.update(city);
    }

    @Override
    public void delete(int id) {
      cityDao.delete(id);
    }

    @Override
    public City getCityById(int id) {
        return cityDao.getCityById(id);
    }


}
