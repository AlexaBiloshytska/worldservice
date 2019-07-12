package com.alexa.worldservice.mapper;

import com.shelberg.entity.Country;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CountryMapperTest {

    @Test
    public void mapRow() throws SQLException {
        CountryMapper countryMapper = new CountryMapper();

        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getString("code")).thenReturn("UKR");
        when(mockResultSet.getString("name")).thenReturn("Ukraine");
        when(mockResultSet.getString("continent")).thenReturn("Europe");
        when(mockResultSet.getString("region")).thenReturn("Eastern Europe");
        when(mockResultSet.getDouble("surfacearea")).thenReturn(603700.0);
        when(mockResultSet.getInt("indepyear")).thenReturn(1991);
        when(mockResultSet.getInt("population")).thenReturn(50456000);
        when(mockResultSet.getString("language")).thenReturn("Belorussian");
        when(mockResultSet.getBoolean("isofficial")).thenReturn(false);
        when(mockResultSet.getDouble("percentage")).thenReturn(0.3);

        Country country = countryMapper.mapRow(mockResultSet);

        assertNotNull(country);

        assertEquals("UKR", country.getCode());
        assertEquals("Ukraine", country.getName());
        assertEquals("Europe", country.getContinent());
        assertEquals("Eastern Europe", country.getRegion());
        assertEquals(603700.0, country.getSurfaceArea(),0.00);
        assertEquals(50456000,country.getPopulation());

    }
}