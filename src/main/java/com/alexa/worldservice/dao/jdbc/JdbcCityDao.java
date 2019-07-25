package com.alexa.worldservice.dao.jdbc;

import com.alexa.worldservice.mapper.SearchCityMapper;
import com.shelberg.entity.SearchCity;
import com.shelberg.search.CitySearchQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcCityDao implements CityDao {
    private final Logger logger = LoggerFactory.getLogger(getClass());
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
    public List<SearchCity> searchCityByCriteria(CitySearchQuery citySearchQuery) {
        String getCityQuery = getCityByCriteriaQuery(citySearchQuery);

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            try(ResultSet resultSet = statement.executeQuery(getCityQuery)){
                List<SearchCity> cities = new ArrayList<>();
                while (resultSet.next()) {
                    cities.add(CITY_MAPPER.mapRow(resultSet, citySearchQuery));
                }
                return cities;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to execute sql query:" + GET_CITY_BY_CRITERIA, e);
        }
    }

    String getCityByCriteriaQuery(CitySearchQuery citySearchQuery) {
        String countryNamePlaceHolder = citySearchQuery.isCountryRequired() ? ",cc.name as countryName " : "";
        String populationPlaceHolder = citySearchQuery.isPopulationRequired() ? ",c.population as population" : "";
        String countryPopulationPlaceHolder = citySearchQuery.isCountryPopulationRequired() ? ",cc.population as countryPopulation" : "";

        StringBuilder stringBuilder = new StringBuilder(
                String.format(GET_CITY_BY_CRITERIA,
                        countryNamePlaceHolder,
                        populationPlaceHolder,
                        countryPopulationPlaceHolder));
        if (citySearchQuery.getCountry() != null) {
            stringBuilder
                    .append(" AND lower(cc.name) like '%")
                    .append(citySearchQuery.getCountry().toLowerCase())
                    .append("%'");
        }
        if (citySearchQuery.getName() != null) {
            stringBuilder.
                    append( " AND lower(c.name) like '%" ).
                    append( citySearchQuery.getName().toLowerCase()).
                    append( "%'");
        }
        if (citySearchQuery.getContinent() != null) {
            stringBuilder
                    .append(" AND lower(cc.continent) like '%" )
                    .append( citySearchQuery.getContinent().toLowerCase() )
                    .append( "%'");
        }

        logger.info("Getting the query with additional parameters: {}", stringBuilder.toString());

        return stringBuilder.toString();
    }
}
