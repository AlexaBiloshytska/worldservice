package alexa.com.worldservice.mapper;

import alexa.com.worldservice.entity.Country;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryMapper {
    public Country mapRow(ResultSet resultSet) throws SQLException {

        Country country = new Country();
        country.setCode(resultSet.getString("code"));
        country.setName(resultSet.getString("name"));
        country.setContinent(resultSet.getString("continent"));
        country.setRegion(resultSet.getString("region"));
        country.setSurfaceArea(resultSet.getFloat("surfacearea"));
        country.setIndepYear(resultSet.getInt("indepyear"));
        country.setPopulation(resultSet.getInt("population"));
        country.setLifeExpectancy(resultSet.getString("lifeexpectancy"));
        country.setGnp(resultSet.getFloat("gnp"));
        country.setGnpOld(resultSet.getFloat("gnpold"));
        country.setLocalMame(resultSet.getString("localname"));
        country.setGovernmentForm(resultSet.getString("governmentform"));
        country.setHeadOfState(resultSet.getString("headOfState"));
        country.setCapital(resultSet.getInt("capital"));
        country.setCodeTwo(resultSet.getString("code2"));

        return country;
    }
}
