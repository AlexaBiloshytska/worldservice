package alexa.com.worldservice.dao;

import alexa.com.worldservice.entity.Country;
import alexa.com.worldservice.entity.CountryLanguageStatistics;

import java.util.List;

public interface CountryDao {
    List<Country> getAll();
    List<CountryLanguageStatistics>getStatistics();
}
