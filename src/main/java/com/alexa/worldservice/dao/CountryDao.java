package com.alexa.worldservice.dao;

import com.alexa.worldservice.entity.CountryLanguageStatistics;

public interface CountryDao {
    CountryLanguageStatistics getStatistics(String name);
}
