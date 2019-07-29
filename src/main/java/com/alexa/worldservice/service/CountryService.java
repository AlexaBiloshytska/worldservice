package com.alexa.worldservice.service;

import com.shelberg.entity.Country;
import com.shelberg.search.CountrySearchQuery;

import java.util.List;

public interface CountryService {
    Country getCountry(String code);

    List<Country> getCountriesByLanguage(String language);

    Country add(Country country);

    int delete(String code);

    Country update(Country country);
    List<Country> getCountriesByCriteria(CountrySearchQuery countrySearchQuery);
}
