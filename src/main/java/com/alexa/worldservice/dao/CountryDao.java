package com.alexa.worldservice.dao;

import com.alexa.worldservice.entity.Country;

import java.util.List;

public interface CountryDao {
    Country getCountry(String name);
    List<Country> getCountriesByLanguage(String language);
}
