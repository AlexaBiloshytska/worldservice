package com.alexa.worldservice.dao.jdbc;

import com.alexa.worldservice.entity.CitySearchCriteria;
import com.shelberg.entity.SearchCity;

import java.util.List;

public interface CityDao {
    List<SearchCity> searchCityByCriteria(CitySearchCriteria citySearchCriteria);
}
