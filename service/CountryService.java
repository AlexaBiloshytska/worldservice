package alexa.com.worldservice.service;

import alexa.com.worldservice.entity.Country;
import alexa.com.worldservice.entity.CountryLanguageStatistics;

import java.util.List;

public interface CountryService {

    List<CountryLanguageStatistics> getStatistics();

}
