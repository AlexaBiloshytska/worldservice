package com.alexa.worldservice.service;

import com.alexa.worldservice.dao.CountryDao;
import com.shelberg.entity.Country;

public class DefaultCountryService implements CountryService {
    private CountryDao countryDao;

    public DefaultCountryService(CountryDao countryDao) {
        this.countryDao = countryDao;
    }

    @Override
    public Country getCountry(String name) {
        return countryDao.getCountry(name);
    }

}
