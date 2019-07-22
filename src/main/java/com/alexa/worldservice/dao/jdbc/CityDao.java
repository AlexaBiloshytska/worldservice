package com.alexa.worldservice.dao.jdbc;

import com.alexa.worldservice.entity.CityCriteria;
import com.alexa.worldservice.entity.SearchCity;

import java.util.List;

public interface CityDao {
    List<SearchCity> searchCityByCriteria(CityCriteria cityCriteria);
}
