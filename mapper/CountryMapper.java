package alexa.com.worldservice.mapper;

import alexa.com.worldservice.entity.Country;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryMapper {
    public Country mapRow(ResultSet resultSet) throws SQLException{
        String code = resultSet.getString("code");
        String name = resultSet.getString("name");
        String continent = resultSet.getString("continent");
        String region = resultSet.getString("region");
        Float  surfaceArea = resultSet.getFloat("surfacearea");
        Integer indepYear = resultSet.getInt("indepyear");
        Integer population = resultSet.getInt("population");
        String lifeExpectancy = resultSet.getString("lifeexpectancy");
        Float  gnp = resultSet.getFloat("gnp");
        Float  gnpOld = resultSet.getFloat("gnpold");
        String localName = resultSet.getString("localname");
        String governmentForm = resultSet.getString("governmentform");
        String headOfState = resultSet.getString("headOfState");
        Integer capital = resultSet.getInt("capital");
        String codeTwo = resultSet.getString("code2");

        Country country = new Country();
        country.setCode(code);
        country.setName(name);
        country.setContinent(continent);
        country.setRegion(region);
        country.setSurfaceArea(surfaceArea);
        country.setIndepYear(indepYear);
        country.setPopulation(population);
        country.setLifeExpectancy(lifeExpectancy);
        country.setGnp(gnp);
        country.setGnpOld(gnpOld);
        country.setLocalMame(localName);
        country.setHeadOfState(headOfState);
        country.setCapital(capital);
        country.setCodeTwo(codeTwo);

        return country;



    }

}
