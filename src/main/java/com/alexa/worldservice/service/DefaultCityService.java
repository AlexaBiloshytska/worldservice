package com.alexa.worldservice.service;

import com.alexa.worldservice.dao.jdbc.CityDao;
import com.shelberg.entity.City;
import com.shelberg.entity.SearchCity;
import com.shelberg.search.CitySearchQuery;

import java.util.List;

public class DefaultCityService implements CityService {
    private CityDao cityDao;

    public DefaultCityService(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    @Override
    public List<SearchCity> getCitiesByCriteria(CitySearchQuery CitySearchQuery) {
        return cityDao.searchCityByCriteria(CitySearchQuery);
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
