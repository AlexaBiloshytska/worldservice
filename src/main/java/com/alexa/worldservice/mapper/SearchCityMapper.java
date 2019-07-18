package com.alexa.worldservice.mapper;

import com.alexa.worldservice.entity.SearchCity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchCityMapper {

    public SearchCity mapRow(ResultSet resultSet) throws SQLException {
        SearchCity city = new SearchCity();
        city.setName(resultSet.getString("name"));
        city.setDistrict(resultSet.getString("district"));

        city.setCountryName(resultSet.getString("countryName"));
        city.setPopulation(resultSet.getInt("population"));
        city.setCountryPopulation(resultSet.getInt("countryPopulation"));

        return city;
    }
}
