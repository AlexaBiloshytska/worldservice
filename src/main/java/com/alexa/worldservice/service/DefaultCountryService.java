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
    public Country getCountry(String code) {
        return countryDao.getCountry(code);
    }

    @Override
    public List<Country> getCountriesByLanguage(String language) {
        return countryDao.getCountriesByLanguage(language);
    }

    @Override
    public Country add(Country country) {
       return countryDao.add(country);
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
    public Country update(Country country) {
        return countryDao.update(country);
    }
}
