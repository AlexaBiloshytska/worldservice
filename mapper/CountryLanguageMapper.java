package alexa.com.worldservice.mapper;

import alexa.com.worldservice.entity.CountryLanguageStatistics;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryLanguageMapper {
    public CountryLanguageStatistics mapRow(ResultSet resultSet) throws SQLException {
        String code = resultSet.getString("code");
        String name = resultSet.getString("name");
        String continent = resultSet.getString("continent");
        String region = resultSet.getString("region");
        Float  surfaceArea = resultSet.getFloat("surfacearea");
        Integer indepYear = resultSet.getInt("indepyear");
        Integer population = resultSet.getInt("population");
        String language = resultSet.getString("language");
        Boolean isOfficial = resultSet.getBoolean("isofficial");
        Integer percentage = resultSet.getInt("percentage");

        CountryLanguageStatistics countryLanguageStatistics = new CountryLanguageStatistics();
        countryLanguageStatistics.setCode(code);
        countryLanguageStatistics.setName(name);
        countryLanguageStatistics.setContinent(continent);
        countryLanguageStatistics.setRegion(region);
        countryLanguageStatistics.setSurfaceArea(surfaceArea);
        countryLanguageStatistics.setIndepYear(indepYear);
        countryLanguageStatistics.setPopulation(population);
        countryLanguageStatistics.setLanguage(language);
        countryLanguageStatistics.setOficial(isOfficial);
        countryLanguageStatistics.setPercentage(percentage);


        return countryLanguageStatistics;



    }


}
