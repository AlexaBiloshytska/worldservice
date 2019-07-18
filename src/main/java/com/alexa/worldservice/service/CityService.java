package com.alexa.worldservice.service;

import com.alexa.worldservice.entity.SearchCity;

import java.util.List;

public interface CityService {
    List<SearchCity> getCitiesByCriteria(Boolean country, Boolean name, Boolean continent);
}
