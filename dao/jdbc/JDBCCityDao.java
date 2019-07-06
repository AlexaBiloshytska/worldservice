package alexa.com.worldservice.dao.jdbc;

import alexa.com.worldservice.dao.CityDao;
import alexa.com.worldservice.entity.City;
import alexa.com.worldservice.entity.Country;
import alexa.com.worldservice.mapper.CityMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JDBCCityDao implements CityDao {
    private DataSource dataSource;
    private static final CityMapper CITY_MAPPER = new CityMapper();
    private static final String GET_ALL_SQL = "select id, name, country_code,district,population from city";


    public JDBCCityDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<City> getAll() {
        List<City> cities = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_SQL);) {
            while (resultSet.next()) {
                City category = CITY_MAPPER.mapRow(resultSet);
                cities.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }
}

