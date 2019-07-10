package com.alexa.worldservice.mapper;

import com.alexa.worldservice.entity.CountryLanguageStatistics;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CountryLanguageMapperTest {

    @Test
    public void mapRow() throws SQLException {
        CountryLanguageMapper countryLanguageMapper = new CountryLanguageMapper();

        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getString("code")).thenReturn("AFG");
        when(mockResultSet.getString("name")).thenReturn("Afghanistan");
        when(mockResultSet.getString("continent")).thenReturn("Asia");
        when(mockResultSet.getString("region")).thenReturn("Southern and Central Asia");
        when(mockResultSet.getDouble("surfaceArea")).thenReturn(652090.0);
        when(mockResultSet.getInt("indepYear")).thenReturn(1919);
        when(mockResultSet.getInt("population")).thenReturn(22720000);
        when(mockResultSet.getString("language")).thenReturn("Balochi");
        when(mockResultSet.getBoolean("isOfficial")).thenReturn(false);
        when(mockResultSet.getDouble("percentage")).thenReturn(0.9);


        CountryLanguageStatistics countryLanguageStatistics = countryLanguageMapper.mapRow(mockResultSet);

        assertNotNull(countryLanguageStatistics);

        assertEquals("AFG", countryLanguageStatistics.getCode());
        assertEquals("Afghanistan", countryLanguageStatistics.getName());
        assertEquals("Asia", countryLanguageStatistics.getContinent());
        assertEquals("Southern and Central Asia", countryLanguageStatistics.getRegion());
        assertEquals(652090.0, countryLanguageStatistics.getSurfaceArea(),652090.00);
        assertEquals(22720000,countryLanguageStatistics.getPopulation());
        assertEquals("Balochi", countryLanguageStatistics.getLanguage());
        assertEquals(0.9, countryLanguageStatistics.getPercentage(),0.9);
        assertEquals(false,countryLanguageStatistics.isOficial());

    }
}