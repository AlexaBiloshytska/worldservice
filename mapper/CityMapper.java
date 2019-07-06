package alexa.com.worldservice.mapper;



import alexa.com.worldservice.entity.City;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CityMapper {
    public City mapRow(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String countryCode = resultSet.getString("countryCode");
        String district = resultSet.getString("district");
        Integer population = resultSet.getInt("population");


        City city = new City();
        city.setId(id);
        city.setName(name);
        city.setCountry_code(countryCode);
        city.setDistrict(district);
        city.setPopulation(population);
        return city;

    }
}
