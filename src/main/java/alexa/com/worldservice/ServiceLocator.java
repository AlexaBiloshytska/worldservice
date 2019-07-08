package alexa.com.worldservice;

import alexa.com.worldservice.dao.CountryDao;
import alexa.com.worldservice.dao.jdbc.JdbcCountryDao;
import alexa.com.worldservice.service.CountryService;
import alexa.com.worldservice.service.DefaultCountryService;
import org.postgresql.ds.PGPoolingDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ServiceLocator {
    private static final Map<Class<?>, Object> LOCATOR = initDefaultDependencies();

    public static void register(Class<?> clazz, Object service) {
        LOCATOR.put(clazz, service);
    }

    private static Map<Class<?>, Object> initDefaultDependencies() {
        Map<Class<?>, Object> map = new HashMap<>();

        PGPoolingDataSource source = getPgPoolingDataSource();

        CountryDao countryDao = new JdbcCountryDao(source);

        //config services
        CountryService countryService = new DefaultCountryService(countryDao);
        map.put(CountryService.class, countryService);

        return map;
    }

    private static PGPoolingDataSource getPgPoolingDataSource() {
        InputStream resourceAsStream = ServiceLocator.class
                .getClassLoader().getResourceAsStream("application.production.properties");

        Properties appProps = new Properties();
        PGPoolingDataSource source = new PGPoolingDataSource();

        try {
            appProps.load(resourceAsStream);
        } catch (IOException e) {
            throw new RuntimeException("Unable to load application configuration");
        }

        source.setServerName(appProps.getProperty("serviceName"));
        source.setDatabaseName(appProps.getProperty("databaseName"));
        source.setUser(appProps.getProperty("user"));
        source.setPassword(appProps.getProperty("password"));
        source.setSsl(Boolean.parseBoolean(appProps.getProperty("ssl")));
        source.setSslfactory(appProps.getProperty("sslFactory"));
        source.setPortNumber(Integer.parseInt(appProps.getProperty("portNumber")));
        source.setMaxConnections(Integer.parseInt(appProps.getProperty("maxConnections")));

        return source;
    }

    public static <T> T get(Class<T> clazz) {
        return (T) clazz.cast(LOCATOR.get(clazz));
    }
}


