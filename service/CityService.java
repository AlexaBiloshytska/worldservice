package alexa.com.worldservice.service;

import alexa.com.worldservice.entity.City;

import java.util.List;

public interface CityService {
    List<City> getAll();
    void  add(City city);
}
