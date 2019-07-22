package com.alexa.worldservice.service;

import com.alexa.worldservice.dao.CountryDao;
import com.shelberg.entity.Country;

import java.util.List;

public class DefaultCountryService implements CountryService {
    private CountryDao countryDao;

    public DefaultCountryService(CountryDao countryDao) {
        this.countryDao = countryDao;
    }

    @Override
    public Country getCountry(String name) {
        return countryDao.getCountry(name);
    }

    @Override
    public List<Country> getCountriesByLanguage(String language) {
        return countryDao.getCountriesByLanguage(language);
    }

    @Override
    public void add(Country country) {
        countryDao.add(country);
    }

    @Override
    public void delete(String name) {
        countryDao.delete(name);
    }

    @Override
    public void update(Country country) {
        countryDao.update(country);
    }
}
