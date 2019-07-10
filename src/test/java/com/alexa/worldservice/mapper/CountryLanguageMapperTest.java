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
        when(mockResultSet.getString("code")).thenReturn("AWS");
        when(mockResultSet.getString("name")).thenReturn("Aruba");
        when(mockResultSet.getString("continent")).thenReturn("North America");
        when(mockResultSet.getString("region")).thenReturn("Caribbean");
        when(mockResultSet.getString("language")).thenReturn("French");
        when(mockResultSet.getDouble("surfaceArea")).thenReturn(193.0);

        CountryLanguageStatistics countryLanguageStatistics = countryLanguageMapper.mapRow(mockResultSet);

        assertNotNull(countryLanguageStatistics);

        assertEquals("AWS", countryLanguageStatistics.getCode());
        assertEquals("Aruba", countryLanguageStatistics.getName());
        assertEquals("North America", countryLanguageStatistics.getContinent());
        assertEquals("Caribbean", countryLanguageStatistics.getRegion());
        assertEquals("French", countryLanguageStatistics.getLanguage());
        assertEquals(193.0, countryLanguageStatistics.getSurfaceArea(),193.00);
    }
}