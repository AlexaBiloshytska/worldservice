package com.alexa.worldservice.mapper;

import com.alexa.worldservice.entity.City;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CityMapper {

    public City mapRow( ResultSet resultSet) throws SQLException{
          City city = new City();
          city.setId(resultSet.getInt("id"));
          city.setName(resultSet.getString("name"));
          city.setCountryCode(resultSet.getString("countryCode"));
          city.setDistrict(resultSet.getString("district"));
          city.setPopulation(resultSet.getInt("population"));

          return city;
    }
}
