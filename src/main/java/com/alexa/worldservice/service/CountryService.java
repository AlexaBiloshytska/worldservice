package com.alexa.worldservice.service;

import com.alexa.worldservice.entity.CountryLanguageStatistics;

public interface CountryService {

    CountryLanguageStatistics getStatistics(String name);

}
