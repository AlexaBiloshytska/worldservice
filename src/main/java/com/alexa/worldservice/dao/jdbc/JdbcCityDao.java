package com.alexa.worldservice.dao.jdbc;

import com.alexa.worldservice.dao.CityDao;
import com.alexa.worldservice.exception.NoDataFoundException;
import com.alexa.worldservice.mapper.CityMapper;
import com.alexa.worldservice.mapper.SearchCityMapper;
import com.shelberg.entity.City;
import com.shelberg.entity.SearchCity;
import com.shelberg.search.CitySearchQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcCityDao implements CityDao {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final SearchCityMapper SEARCH_CITY_MAPPER = new SearchCityMapper();
    private static final CityMapper CITY_MAPPER = new CityMapper();
    private static final String GET_CITY_BY_CRITERIA =
            "select c.name, c.district %s %s %s " +
                    "from city as c join country as cc on c.country_code = cc.code " +
                    "where 1=1 ";

    private static final String ADD_CITY = "INSERT INTO city" +
            "(name, country_code,district,population) values(?,?,?,?)";

    private static final String UPDATE_CITY = "UPDATE city set name=?, " +
            " country_code =?, district =?,population =? where id =?";

    private static final String DELETE_CITY = "delete from city where id =?";

    private static final String GET_CITY_BY_ID = "select c.id, c.name, c.district, c.country_code, " +
            "c.population from city c where c.id = ?";


    private DataSource dataSource;

    public JdbcCityDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<SearchCity> searchCityByCriteria(CitySearchQuery citySearchQuery) {
        String getCityQuery = getCityByCriteriaQuery(citySearchQuery);

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            try (ResultSet resultSet = statement.executeQuery(getCityQuery)) {
                List<SearchCity> cities = new ArrayList<>();
                while (resultSet.next()) {
                    cities.add(SEARCH_CITY_MAPPER.mapRow(resultSet, citySearchQuery));
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
                    append(" AND lower(c.name) like '%").
                    append(citySearchQuery.getName().toLowerCase()).
                    append("%'");
        }
        if (citySearchQuery.getContinent() != null) {
            stringBuilder
                    .append(" AND lower(cc.continent) like '%")
                    .append(citySearchQuery.getContinent().toLowerCase())
                    .append("%'");
        }

        logger.info("Getting the query with additional parameters: {}", stringBuilder.toString());

        return stringBuilder.toString();
    }

    @Override
    public City add(City city) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement addCity = connection.prepareStatement(ADD_CITY, PreparedStatement.RETURN_GENERATED_KEYS)) {

            addCity.setString(1, city.getName());
            addCity.setString(2, city.getCountryCode());
            addCity.setString(3, city.getDistrict());
            addCity.setInt(4, city.getPopulation());

            addCity.execute();
            logger.info("City with name: {} is successfully inserted", city.getName());


            ResultSet generatedKeys = addCity.getGeneratedKeys();
            if (generatedKeys.next()) {
                return CITY_MAPPER.mapRow(generatedKeys);
            }
            throw new RuntimeException("Unable to get generated keys after city insert: " + city);

        } catch (SQLException e) {
            throw new RuntimeException("City insertion is failed: " + city, e);
        }
    }

    @Override
    public City update(City city) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement updateCity = connection.prepareStatement(UPDATE_CITY, PreparedStatement.RETURN_GENERATED_KEYS)) {

            updateCity.setString(1, city.getName());
            updateCity.setString(2, city.getCountryCode());
            updateCity.setString(3, city.getDistrict());
            updateCity.setInt(4, city.getPopulation());
            updateCity.setInt(5, city.getId());

            updateCity.executeUpdate();

            ResultSet generatedKeys = updateCity.getGeneratedKeys();
            if (generatedKeys.next()) {
                return CITY_MAPPER.mapRow(generatedKeys);
            }
            throw new RuntimeException("Unable to get generated keys after city update: " + city);

        } catch (SQLException e) {
            throw new RuntimeException("City update is failed: " + city, e);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CITY)) {

            preparedStatement.setInt(1, id);

            preparedStatement.execute();

            logger.info("City with id {} is successfully deleted ", id);

        } catch (SQLException e) {
            logger.error("City deletion with id {} is failed ", id, e);
        }
    }

    @Override
    public City getCityById(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_CITY_BY_ID)) {

            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    logger.error("resultSet is empty");
                    throw new NoDataFoundException("Non-empty resultSet expected");
                }
                return CITY_MAPPER.mapRow(resultSet);
            }
        } catch (SQLException e) {
            logger.warn("Unable to get city with id {}", id, e);
            throw new RuntimeException("Unable to get city with id = " + id, e);
        }
    }
}
