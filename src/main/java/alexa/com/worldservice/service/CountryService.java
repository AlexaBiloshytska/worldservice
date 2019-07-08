package alexa.com.worldservice.service;

import alexa.com.worldservice.entity.CountryLanguageStatistics;

public interface CountryService {

    CountryLanguageStatistics getStatistics(String name);

}
