package com.alexa.worldservice.mapper;

import com.alexa.worldservice.entity.CitySearchCriteria;
import com.alexa.worldservice.entity.SearchCity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchCityMapper {

    public SearchCity mapRow(ResultSet resultSet, CitySearchCriteria citySearchCriteria) throws SQLException {
        SearchCity city = new SearchCity();
        city.setName(resultSet.getString("name"));
        city.setDistrict(resultSet.getString("district"));
        if (citySearchCriteria.isCountryRequired()) {
            city.setCountryName(resultSet.getString("countryName"));
        }
        if (citySearchCriteria.isPopulationRequired()) {
            city.setPopulation(resultSet.getInt("population"));
        }
        if (citySearchCriteria.isCountryPopulationRequired()) {
            city.setCountryPopulation(resultSet.getInt("countryPopulation"));
        }
        return city;
    }
}
