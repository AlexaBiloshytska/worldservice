package com.alexa.worldservice.service;

import com.alexa.worldservice.dao.CountryDao;
import com.alexa.worldservice.entity.CountryLanguageStatistics;

public class DefaultCountryService implements CountryService {
    private CountryDao countryDao;

    public DefaultCountryService(CountryDao countryDao) {
        this.countryDao = countryDao;
    }

    @Override
    public CountryLanguageStatistics getStatistics(String name) {
        return countryDao.getStatistics(name);
    }

}
