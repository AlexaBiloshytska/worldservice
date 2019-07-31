package com.alexa.worldservice.service;

import com.shelberg.entity.Country;
import com.shelberg.search.CountrySearchQuery;

import java.util.List;

public interface CountryService {
    Country getCountryStatistics(String code);

    List<Country> getCountriesByLanguage(String language);

    List<Country> getCountriesByCriteria(CountrySearchQuery countrySearchQuery);

    Country getByCode(String code);

    Country add(Country country);

    int delete(String code);

    Country update(Country country);

}
