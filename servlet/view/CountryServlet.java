package alexa.com.worldservice.servlet.view;

import alexa.com.worldservice.ServiceLocator;
import alexa.com.worldservice.entity.CountryLanguageStatistics;
import alexa.com.worldservice.service.CountryService;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet(urlPatterns = "/countries")
public class CountryServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private CountryService countryService = ServiceLocator.get(CountryService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long startTime = System.currentTimeMillis();

        String countryName = request.getParameter("name");
        logger.info("Getting country with country name {} ", countryName);

        CountryLanguageStatistics getCountryByName = countryService.getStatistics(countryName);

        XmlMapper xmlMapper = new XmlMapper();
        String xml = xmlMapper.writeValueAsString(getCountryByName);

        response.getWriter().print(xml);

        logger.info("Finished getting country language statistics in {} ms", startTime - System.currentTimeMillis());
    }

}
