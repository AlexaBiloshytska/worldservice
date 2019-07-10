package com.alexa.worldservice.mapper;

import com.alexa.worldservice.entity.CountryLanguageStatistics;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryLanguageMapper {
    public CountryLanguageStatistics mapRow(ResultSet resultSet) throws SQLException {

        CountryLanguageStatistics countryLanguageStatistics = new CountryLanguageStatistics();
        countryLanguageStatistics.setCode(resultSet.getString("code"));
        countryLanguageStatistics.setName(resultSet.getString("name"));
        countryLanguageStatistics.setContinent(resultSet.getString("continent"));
        countryLanguageStatistics.setRegion(resultSet.getString("region"));
        countryLanguageStatistics.setSurfaceArea(resultSet.getFloat("surfacearea"));
        countryLanguageStatistics.setIndepYear(resultSet.getInt("indepyear"));
        countryLanguageStatistics.setPopulation(resultSet.getInt("population"));
        countryLanguageStatistics.setLanguage(resultSet.getString("language"));
        countryLanguageStatistics.setOficial(resultSet.getBoolean("isofficial"));
        countryLanguageStatistics.setPercentage(resultSet.getInt("percentage"));

        return countryLanguageStatistics;
    }
}
