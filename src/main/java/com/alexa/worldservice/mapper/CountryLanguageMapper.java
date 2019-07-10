package com.alexa.worldservice.mapper;

import com.alexa.worldservice.entity.CountryLanguage;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryLanguageMapper {
    public CountryLanguage mapRow(ResultSet resultSet) throws SQLException {
        CountryLanguage countryLanguage = new CountryLanguage();

        countryLanguage.setLanguage(resultSet.getString("language"));
        countryLanguage.setOficial(resultSet.getBoolean("isofficial"));
        countryLanguage.setPercentage(resultSet.getFloat("percentage"));

        return countryLanguage;
    }
}
