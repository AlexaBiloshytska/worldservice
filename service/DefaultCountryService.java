package alexa.com.worldservice.service;

import alexa.com.worldservice.dao.CountryDao;
import alexa.com.worldservice.entity.CountryLanguageStatistics;

public class DefaultCountryService implements CountryService {
    private CountryDao countryDao;

    public DefaultCountryService(CountryDao countryDao) {
        this.countryDao = countryDao;
    }

    @Override
    public CountryLanguageStatistics getStatistics(String name) {
        return countryDao.getStatistics(name);
    }

}
