package com.alexa.worldservice;

import com.alexa.worldservice.dao.CountryDao;
import com.alexa.worldservice.dao.jdbc.JdbcCountryDao;
import com.alexa.worldservice.service.CountryService;
import com.alexa.worldservice.service.DefaultCountryService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
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
        InputStream resourceAsStream = ServiceLocator.class
                .getClassLoader().getResourceAsStream("application.production.properties");

        Properties appProps = new Properties();

        try {
            appProps.load(resourceAsStream);
        } catch (IOException e) {
            throw new RuntimeException("Unable to load application configuration");
        }

        HikariConfig config = new HikariConfig();

        String databaseUrl = System.getenv("DATABASE_URL");
        System.out.println(databaseUrl);

        config.setJdbcUrl("jdbc:postgresql://ec2-54-247-170-5.eu-west-1.compute.amazonaws.com:5432/d2kaipcd4djo95");
        config.setDriverClassName("org.postgresql.Driver");
        config.setUsername("quschdkeukipgt");
        config.setPassword("b2d6c23fc18f05db0abce58f14aa3895bcdc12cc5aa6e70c8ce9454e7d1afa47");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        return new HikariDataSource(config);
    }
}


