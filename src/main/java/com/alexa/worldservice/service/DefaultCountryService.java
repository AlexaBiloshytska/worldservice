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
    public List<Country> getCountriesByLanguage(String language) { return countryDao.getCountriesByLanguage(language);
    }

    @Override
    public List<Country> getCountriesByCriteria(String name, String continent, Integer population, Integer page) {
        return countryDao.searchByCriteria(name,continent,population,page);
    }

}
