package com.alexa.worldservice.dao;


import com.shelberg.entity.Country;
import java.util.List;

public interface CountryDao {
    Country getCountry(String name);
    List<Country> getCountriesByLanguage(String language);
    List<Country> searchByName(String name, int page);
}
