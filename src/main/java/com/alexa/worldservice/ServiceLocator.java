package com.alexa.worldservice;

import com.alexa.worldservice.dao.CountryDao;
import com.alexa.worldservice.dao.jdbc.JdbcCountryDao;
import com.alexa.worldservice.service.CountryService;
import com.alexa.worldservice.service.DefaultCountryService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ServiceLocator {
    private static final Logger logger = LoggerFactory.getLogger(ServiceLocator.class);
    private static final String localPropFileName = "application.local.properties";
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
        String driverName;
        String poolSize;
        String databaseUrl = System.getenv("DATABASE_URL");

        if (databaseUrl == null) { // use local props
            Properties appProps = loadLocalProperties();
            logger.info("Getting properties from a local properties file");
            databaseUrl = appProps.getProperty("dbUrl");
            driverName = appProps.getProperty("driverName");
            poolSize = appProps.getProperty("maxConnections");
        } else { // use Heroku env variable
            logger.info("Getting properties from an environment variables");
            driverName = "org.postgresql.Driver";
            poolSize = System.getenv("maxConnections");
        }

        try {
            URI uri = new URI(databaseUrl);
            String username = uri.getUserInfo().split(":")[0];
            String password = uri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + uri.getHost() + ':' + uri.getPort() + uri.getPath();

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(dbUrl);
            config.setDriverClassName(driverName);
            config.setUsername(username);
            config.setPassword(password);
            config.setMaximumPoolSize(Integer.parseInt(poolSize));

            logger.info("Datasource have been configured with following properties:{},{},{},{},{}",
                    driverName, poolSize, dbUrl, username, password);

            return new HikariDataSource(config);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Unable to get database URL", e);
        }
    }

    private static Properties loadLocalProperties() {
        try (InputStream resourceAsStream = ServiceLocator.class.getClassLoader().getResourceAsStream(localPropFileName)) {
            Properties appProps = new Properties();
            appProps.load(resourceAsStream);
            return appProps;
        } catch (IOException e) {
            throw new RuntimeException("Unable to load application configuration", e);
        }
    }
}


