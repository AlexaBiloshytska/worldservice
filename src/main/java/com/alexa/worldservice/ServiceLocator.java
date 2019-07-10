package com.alexa.worldservice;

import com.alexa.worldservice.dao.CountryDao;
import com.alexa.worldservice.dao.jdbc.JdbcCountryDao;
import com.alexa.worldservice.service.CountryService;
import com.alexa.worldservice.service.DefaultCountryService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ServiceLocator {
    private static final Map<Class<?>, Object> LOCATOR = initDefaultDependencies();
    private static Map<Class<?>, Object> initDefaultDependencies() {

        Map<Class<?>, Object> map = new HashMap<>();

        //config dao
        HikariDataSource dataSource = getHikariDataSource();
        CountryDao countryDao = new JdbcCountryDao(dataSource);

        //config services
        CountryService countryService = new DefaultCountryService(countryDao);

        map.put(CountryService.class, countryService);

        return map;
    }

    public static <T> T get(Class<T> clazz) {
        return clazz.cast(LOCATOR.get(clazz));
    }

    private static HikariDataSource getHikariDataSource() {
        HikariConfig config = new HikariConfig();
        try {
            URI databaseUrl =  new URI(System.getenv("DATABASE_URL"));
            String username = databaseUrl.getUserInfo().split(":")[0];
            String password = databaseUrl.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + databaseUrl.getHost() + ':' + databaseUrl.getPort() + databaseUrl.getPath();

            config.setJdbcUrl(dbUrl);
            config.setDriverClassName("org.postgresql.Driver");
            config.setUsername(username);
            config.setPassword(password);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Unable to get database URL");
        }

        return new HikariDataSource(config);
    }
}


