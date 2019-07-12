package com.alexa.worldservice.dao;

import com.shelberg.entity.Country;

public interface CountryDao {
    Country getCountry(String name);
}
