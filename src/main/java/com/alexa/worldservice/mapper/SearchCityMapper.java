package com.alexa.worldservice.mapper;

import com.shelberg.entity.SearchCity;
import com.shelberg.search.CitySearchQuery;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchCityMapper {

    public SearchCity mapRow(ResultSet resultSet, CitySearchQuery citySearchQuery) throws SQLException {
        SearchCity city = new SearchCity();
        city.setName(resultSet.getString("name"));
        city.setDistrict(resultSet.getString("district"));
        if (citySearchQuery.isCountryRequired()) {
            city.setCountryName(resultSet.getString("countryName"));
        }
        if (citySearchQuery.isPopulationRequired()) {
            city.setPopulation(resultSet.getInt("population"));
        }
        if (citySearchQuery.isCountryPopulationRequired()) {
            city.setCountryPopulation(resultSet.getInt("countryPopulation"));
        }
        return city;
    }
}
