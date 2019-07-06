package alexa.com.worldservice;

import alexa.com.worldservice.dao.CityDao;
import alexa.com.worldservice.dao.CountryDao;
import alexa.com.worldservice.dao.jdbc.JDBCCityDao;
import alexa.com.worldservice.dao.jdbc.JdbcCountryDao;
import alexa.com.worldservice.service.CityService;
import alexa.com.worldservice.service.CountryService;
import alexa.com.worldservice.service.DefaultCityService;
import alexa.com.worldservice.service.DefaultCountryService;
import org.postgresql.ds.PGPoolingDataSource;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {
    private static final Map<Class<?>, Object> LOCATOR = initDefaultDependencies();

    public static void register(Class<?> clazz, Object service) {
        LOCATOR.put(clazz, service);

    }

    public static Map<Class<?>, Object> initDefaultDependencies() {
        Map<Class<?>, Object> map = new HashMap<>();

        //config DAO
        PGPoolingDataSource source = new PGPoolingDataSource();
        source.setServerName("localhost");
        source.setDatabaseName("world");
        source.setUser("postgres");
        source.setPassword("postgres");
        //source.setSsl(true);
        //source.setSslfactory("org.postgresql.ssl.NonValidatingFactory");
        source.setPortNumber(5432);
        source.setMaxConnections(10);

        CityDao cityDao = new JDBCCityDao(source);
        CountryDao countryDao = new JdbcCountryDao(source);

        //config services
        CountryService countryService = new DefaultCountryService(countryDao);
        map.put(CountryService.class,countryService);

        CityService cityService = new DefaultCityService(cityDao);
        map.put(CityService.class,cityService);

        return map;
    }
    public static <T> T get(Class<?> clazz){
        return (T) clazz.cast(LOCATOR.get(clazz));
    }
}


