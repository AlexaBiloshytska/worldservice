package com.alexa.worldservice.service;

import com.shelberg.entity.SearchCity;
import com.shelberg.search.CitySearchQuery;

import java.util.List;

public interface CityService {
    List<SearchCity> getCitiesByCriteria(CitySearchQuery citySearchQuery);
}
