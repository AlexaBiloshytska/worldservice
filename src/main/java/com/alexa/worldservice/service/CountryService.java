package com.alexa.worldservice.service;

import com.shelberg.entity.Country;
import com.shelberg.search.CountrySearchQuery;

import java.util.List;

public interface CountryService {
    Country getCountry(String name);
    List<Country> getCountriesByLanguage(String language);
    List<Country> getCountriesByCriteria(CountrySearchQuery countrySearchQuery);
}
