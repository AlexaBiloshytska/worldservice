package com.alexa.worldservice.mapper;

import com.shelberg.entity.Language;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LanguageMapper {
    public Language mapRow(ResultSet resultSet) throws SQLException {
        Language language = new Language();

        language.setName(resultSet.getString("language"));
        language.setOfficial(resultSet.getBoolean("isofficial"));
        language.setPercentage(resultSet.getDouble("percentage"));

        return language;
    }
}
