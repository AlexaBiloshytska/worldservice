package com.alexa.worldservice.service;

import com.shelberg.entity.Country;

import java.util.List;

public interface CountryService {
    Country getCountry(String name);
    List<Country> getCountriesByLanguage(String language);
}
