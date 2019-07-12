package com.alexa.worldservice.mapper;


import com.shelberg.entity.Country;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryMapper {

    public Country mapRow(ResultSet resultSet) throws SQLException {

        Country country = new Country();
        country.setCode(resultSet.getString("code"));
        country.setName(resultSet.getString("name"));
        country.setContinent(resultSet.getString("continent"));
        country.setRegion(resultSet.getString("region"));
        country.setSurfaceArea(resultSet.getDouble("surfacearea"));
        country.setIndepYear(resultSet.getInt("indepyear"));
        country.setPopulation(resultSet.getInt("population"));
        country.setLifeExpectancy(resultSet.getDouble("lifeexpectancy"));
        country.setGovernmentForm(resultSet.getString("governmentform"));
        country.setGovernmentForm(resultSet.getString("headofstate"));
        country.setGovernmentForm(resultSet.getString("capital"));

        return country;
    }
}