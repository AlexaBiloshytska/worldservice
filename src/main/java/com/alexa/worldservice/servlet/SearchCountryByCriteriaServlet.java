package com.alexa.worldservice.servlet;

import com.alexa.worldservice.ServiceLocator;
import com.alexa.worldservice.constant.MimeType;
import com.alexa.worldservice.service.CountryService;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.shelberg.entity.Country;
import com.shelberg.entity.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/api/v1/search/country")
public class SearchCountryByCriteriaServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Integer LIMIT = 5;
    private final XmlMapper xmlMapper = ServiceLocator.get(XmlMapper.class);
    private final CountryService countryService = ServiceLocator.get(CountryService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String continent = request.getParameter("continent");

        String populationParam = request.getParameter("population");
        Integer population = populationParam != null ? Integer.valueOf(populationParam) : null;

        String pageParam = request.getParameter("page");
        Integer page = pageParam != null ? Integer.valueOf(pageParam) : 1;

        String limitParam = request.getParameter("limit");
        Integer limit = limitParam != null ? Integer.valueOf(limitParam) : LIMIT;

        String acceptType = request.getHeaders("Accept").nextElement();

        logger.info("Getting country with parameters: {},{},{},{},{}", name, continent, population, page, limit);

        if (acceptType.contains(MimeType.APPLICATION_XML.getValue())) {
            List<Country> country = countryService.getCountriesByCriteria(name, continent, population, page, limit);
            String xml = xmlMapper.writerWithView(Views.CountryData.class).writeValueAsString(country);
            response.setContentType(MimeType.APPLICATION_XML.getValue());
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().print(xml);
        }
    }

}
