package alexa.com.worldservice.dao;

import alexa.com.worldservice.entity.CountryLanguageStatistics;

public interface CountryDao {
    CountryLanguageStatistics getStatistics(String name);
}
