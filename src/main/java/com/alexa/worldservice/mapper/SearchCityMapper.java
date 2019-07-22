package com.alexa.worldservice.mapper;

import com.alexa.worldservice.entity.CityCriteria;
import com.alexa.worldservice.entity.SearchCity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchCityMapper {

    public SearchCity mapRow(ResultSet resultSet, CityCriteria cityCriteria) throws SQLException {
        SearchCity city = new SearchCity();
        city.setName(resultSet.getString("name"));
        city.setDistrict(resultSet.getString("district"));
        if (cityCriteria.isCountryRequired()) {
            city.setCountryName(resultSet.getString("countryName"));
        }
        if (cityCriteria.isPopulationRequired()) {
            city.setPopulation(resultSet.getInt("population"));
        }
        if (cityCriteria.isCountryPopulationRequired()) {
            city.setCountryPopulation(resultSet.getInt("countryPopulation"));
        }
        return city;
    }
}
