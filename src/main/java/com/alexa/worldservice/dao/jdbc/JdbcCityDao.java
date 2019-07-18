package com.alexa.worldservice.dao.jdbc;

import com.alexa.worldservice.entity.SearchCity;
import com.alexa.worldservice.mapper.SearchCityMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcCityDao implements CityDao {
    private static final SearchCityMapper CITY_MAPPER = new SearchCityMapper();
    private static final String GET_CITY_BY_CRITERIA =
            "select c.name, c.district %s %s %s " +
                    "from city as c join country as cc on c.country_code = cc.code " +
                    "where 1=1 ";

    private DataSource dataSource;

    public JdbcCityDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<SearchCity> searchCityByCriteria(Boolean country, Boolean name, Boolean continent) {
       String criteriaCityQuery = getCityByCriteriaQuery(country,name, continent);

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(criteriaCityQuery);
            List<SearchCity> cities = new ArrayList<>();
            while (resultSet.next()) {
                cities.add(CITY_MAPPER.mapRow(resultSet));
            }
            return cities;

        } catch (SQLException e) {
            throw new RuntimeException("Unable to execute sql query: " + GET_CITY_BY_CRITERIA, e);
        }
    }

    String getCityByCriteriaQuery(boolean countryRequired,
                                  boolean populationRequired, boolean countryPopulationRequired) {
        String defaultQuery = GET_CITY_BY_CRITERIA;

        String countryNamePlaceHolder = countryRequired ? ",cc.name as countryName " : "";
        String populationPlaceHolder = populationRequired ? ",c.population as population" : "";
        String countryPopulationPlaceHolder = countryPopulationRequired?",cc.population as countryPopulation": "";

        defaultQuery = String.format(defaultQuery, countryNamePlaceHolder,populationPlaceHolder,countryPopulationPlaceHolder);

        return defaultQuery;
    }
}
