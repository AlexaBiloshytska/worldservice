package alexa.com.worldservice.dao;

import alexa.com.worldservice.entity.CountryLanguageStatistics;

import java.util.List;

public interface CountryDao {
    List<CountryLanguageStatistics> getStatistics();
}
