package com.alexa.worldservice.dao.jdbc;

import com.alexa.worldservice.entity.SearchCity;
import com.alexa.worldservice.mapper.SearchCityMapper;
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
    public List<SearchCity> searchCityByCriteria(boolean countryRequired, boolean populationRequired, boolean countryPopulationRequired, String country,
                                                 String name, String continent) {
        String criteriaCityQuery = getCityByCriteriaQuery(countryRequired,
                populationRequired,
                countryPopulationRequired,
                country,
                name,
                continent);

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(criteriaCityQuery);
            List<SearchCity> cities = new ArrayList<>();
            while (resultSet.next()) {
                cities.add(CITY_MAPPER.mapRow(resultSet, countryRequired, populationRequired, countryPopulationRequired));
            }
            return cities;

        } catch (SQLException e) {
            throw new RuntimeException("Unable to execute sql query: {}" + GET_CITY_BY_CRITERIA, e);
        }
    }

    String getCityByCriteriaQuery(boolean countryRequired,
                                  boolean populationRequired,
                                  boolean countryPopulationRequired,
                                  String country,
                                  String name,
                                  String continent) {
        String defaultQuery = GET_CITY_BY_CRITERIA;

        String countryNamePlaceHolder = countryRequired ? ",cc.name as countryName " : "";
        String populationPlaceHolder = populationRequired ? ",c.population as population" : "";
        String countryPopulationPlaceHolder = countryPopulationRequired ? ",cc.population as countryPopulation" : "";

        defaultQuery = String.format(defaultQuery, countryNamePlaceHolder, populationPlaceHolder, countryPopulationPlaceHolder);

        if (country != null) {
            defaultQuery = defaultQuery + " AND lower(cc.name) as country like '%" + country.toLowerCase() + "%'";
        }
        if (name != null) {
            defaultQuery = defaultQuery + " AND lower(c.name) like '%" + name.toLowerCase() + "%'";
        }
        if (continent != null) {
            defaultQuery = defaultQuery + " AND lower(cc.continent) like '%" + continent.toLowerCase() + "%'";
        }
        logger.info("Getting the query with additional parameters: {}",defaultQuery );

        return defaultQuery;
    }
}
