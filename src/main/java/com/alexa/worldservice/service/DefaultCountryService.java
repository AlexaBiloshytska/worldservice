package com.alexa.worldservice.service;

import com.alexa.worldservice.dao.CountryDao;
import com.shelberg.entity.Country;
import com.shelberg.search.CountrySearchQuery;

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
    public int delete(String code) {
        return countryDao.delete(code);
    }

    @Override
    public List<Country> getCountriesByCriteria(CountrySearchQuery countrySearchQuery) {
        return countryDao.searchByCriteria(countrySearchQuery);
    }

    @Override
    public int update(Country country) {
        return countryDao.update(country);
    }
}
