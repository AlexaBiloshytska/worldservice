package com.alexa.worldservice.dao.jdbc;

import com.alexa.worldservice.entity.City;

import javax.sql.DataSource;
import java.util.List;

public class JdbcCityDao implements CityDao {
    private static final String GET_CITY_BY_CRITERIA =
            "select c.name, c.district from city as c " +
                    "where 1=1";

    private DataSource dataSource;

    public JdbcCityDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<City> searchCityByCriteria(String country, String name, String continent) {
       getGetCityByCriteriaQuery(country,name,continent);

        return null;
    }

    String getGetCityByCriteriaQuery(String country, String name, String continent) {
        String defaultQuery = GET_CITY_BY_CRITERIA;
        if (country != null && !country.isEmpty()) {
            defaultQuery = defaultQuery + " c.name" + " left join country as cc on c.country_code = cc.code"+" AND lower(cc.country) like '%" + name.toLowerCase() + "%'";
        }
        if (name != null && !name.isEmpty()) {
            defaultQuery = defaultQuery + " c.population" + " left join country as cc on c.country_code = cc.code "+" AND lower(c.name) like '%" + name.toLowerCase() + "%'";
        }
        if (continent != null && !continent.isEmpty()){
            defaultQuery = defaultQuery +" cc.population" + " left join country as cc on c.country_code = cc.code "+ " AND lower(c.continent) = '" + continent.toLowerCase() + "'";

        }
        return defaultQuery;
    }
}
