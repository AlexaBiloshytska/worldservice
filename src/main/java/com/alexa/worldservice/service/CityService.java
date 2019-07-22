package com.alexa.worldservice.service;

import com.alexa.worldservice.entity.CitySearchCriteria;
import com.alexa.worldservice.entity.SearchCity;

import java.util.List;

public interface CityService {
    List<SearchCity> getCitiesByCriteria(CitySearchCriteria citySearchCriteria);
}
