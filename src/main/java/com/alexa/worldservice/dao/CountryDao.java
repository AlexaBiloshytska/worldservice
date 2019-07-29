package com.alexa.worldservice.dao;


import com.shelberg.entity.Country;
import com.shelberg.search.CountrySearchQuery;


import java.util.List;

public interface CountryDao {
    Country getCountry(String name);

    List<Country> getCountriesByLanguage(String language);

    Country add(Country country);

    int delete(String code);

    Country update(Country country);

    Country getCountryByCode (String code);

    List<Country> searchByCriteria(CountrySearchQuery countrySearchQuery);
}
