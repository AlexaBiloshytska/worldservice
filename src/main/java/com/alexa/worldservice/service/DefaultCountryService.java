package com.alexa.worldservice.service;

import com.alexa.worldservice.dao.CountryDao;
import com.alexa.worldservice.entity.CountrySearchCriteria;
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
    public List<Country> getCountriesByCriteria(CountrySearchCriteria countrySearchCriteria) {
        return countryDao.searchByCriteria(countrySearchCriteria);
    }

}
