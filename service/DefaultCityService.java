package alexa.com.worldservice.service;

import alexa.com.worldservice.dao.CityDao;
import alexa.com.worldservice.entity.City;

import java.util.List;

public class DefaultCityService implements CityService {
    private CityDao cityDao;

    public DefaultCityService(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    @Override
    public List<City> getAll() {
        return cityDao.getAll();
    }

    @Override
    public void add(City city) {

    }
}
