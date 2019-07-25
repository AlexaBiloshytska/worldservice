package com.alexa.worldservice.mapper;

import com.shelberg.entity.SearchCity;
import com.shelberg.search.CitySearchQuery;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchCityMapperTest {
    @Test
    public void mapRow() throws SQLException {
        SearchCityMapper cityMapper = new SearchCityMapper();
        CitySearchQuery citySearchQuery = new CitySearchQuery.Builder("a", "b", "c")
                .countryRequired(true)
                .populationRequired(true)
                .countryPopulationRequired(true)
                .build();

        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getString("name")).thenReturn("Chernihiv");
        when(mockResultSet.getString("district")).thenReturn("Europe");
        when(mockResultSet.getString("countryName")).thenReturn("Ukraine");
        when(mockResultSet.getInt("population")).thenReturn(1111);
        when(mockResultSet.getInt("countryPopulation")).thenReturn(2222);

        SearchCity city = cityMapper.mapRow(mockResultSet, citySearchQuery);

        Assert.assertNotNull(city);
        Assert.assertEquals("Chernihiv", city.getName());
        Assert.assertEquals("Europe", city.getDistrict());
        Assert.assertEquals("Ukraine", city.getCountryName());
        Assert.assertEquals(1111, city.getPopulation(), 1);
        Assert.assertEquals(2222, city.getCountryPopulation(), 1);
    }
}
