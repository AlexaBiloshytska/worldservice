package alexa.com.worldservice.servlet.view;

import alexa.com.worldservice.entity.Country;
import alexa.com.worldservice.entity.CountryLanguageStatistics;
import alexa.com.worldservice.service.CountryService;
import alexa.com.worldservice.ServiceLocator;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet(urlPatterns = "/countries")
public class CountryServlet extends HttpServlet {
    private CountryService countryService = ServiceLocator.get(CountryService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<CountryLanguageStatistics> countries = countryService.getStatistics();

        XmlMapper xmlMapper = new XmlMapper();
        String xml = xmlMapper.writeValueAsString(countries);
        response.getWriter().print(xml);

    }

}
