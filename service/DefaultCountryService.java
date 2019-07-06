package alexa.com.worldservice.service;

import alexa.com.worldservice.dao.CountryDao;
import alexa.com.worldservice.entity.Country;
import alexa.com.worldservice.entity.CountryLanguageStatistics;

import java.util.List;

public class DefaultCountryService implements CountryService {
    private CountryDao countryDao;

    public DefaultCountryService(CountryDao countryDao) {
        this.countryDao = countryDao;
    }

    @Override
    public List<Country> getAll() {
        return countryDao.getAll();
    }

    @Override
    public void add(Country country) {

    }

    @Override
    public List<CountryLanguageStatistics> getStatistics() {
        return countryDao.getStatistics();
    }


}
