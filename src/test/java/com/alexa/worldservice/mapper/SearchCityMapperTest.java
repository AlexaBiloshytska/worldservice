package com.alexa.worldservice.mapper;

import com.alexa.worldservice.entity.CitySearchCriteria;
import com.alexa.worldservice.entity.SearchCity;
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
        CitySearchCriteria c = new CitySearchCriteria();

        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getString("name")).thenReturn("Chernihiv");
        when(mockResultSet.getString("district")).thenReturn("Europe");
        when(mockResultSet.getString("countryName")).thenReturn("Ukraine");
        when(mockResultSet.getInt("population")).thenReturn(1111);
        when(mockResultSet.getInt("countryPopulation")).thenReturn(2222);

        SearchCity city = cityMapper.mapRow(mockResultSet,c);

        Assert.assertNotNull(city);
        Assert.assertEquals("Chernihiv", city.getName());
        Assert.assertEquals("Europe",city.getDistrict());
        Assert.assertEquals("Ukraine", city.getCountryName());
        Assert.assertEquals(1111,city.getPopulation(), 0000);
        Assert.assertEquals(2222,city.getCountryPopulation(), 0000);


    }
}
