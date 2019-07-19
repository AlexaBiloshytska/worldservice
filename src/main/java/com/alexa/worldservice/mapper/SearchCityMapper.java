package com.alexa.worldservice.mapper;

import com.alexa.worldservice.entity.SearchCity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchCityMapper {

    public SearchCity mapRow(ResultSet resultSet, boolean countryRequired, boolean populationRequied, boolean countryPopulationRequired) throws SQLException {
        SearchCity city = new SearchCity();
        city.setName(resultSet.getString("name"));
        city.setDistrict(resultSet.getString("district"));
        if (countryRequired) {
            city.setCountryName(resultSet.getString("countryName"));
        }
        if (populationRequied) {
            city.setPopulation(resultSet.getInt("population"));
        }
        if (countryPopulationRequired) {
            city.setCountryPopulation(resultSet.getInt("countryPopulation"));
        }
        return city;
    }
}
