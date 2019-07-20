package com.alexa.worldservice.dao.jdbc;

import com.shelberg.entity.SearchCity;
import com.shelberg.search.CitySearchQuery;

import java.util.List;

public interface CityDao {
    List<SearchCity> searchCityByCriteria(CitySearchQuery CitySearchQuery);
}
