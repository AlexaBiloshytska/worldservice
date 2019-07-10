package com.alexa.worldservice.dao;

import com.alexa.worldservice.entity.Country;

public interface CountryDao {
    Country getCountry(String name);
}
