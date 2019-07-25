package com.alexa.worldservice.servlet;

import com.alexa.worldservice.ServiceLocator;
import com.alexa.worldservice.constant.MimeType;
import com.shelberg.search.CitySearchQuery;
import com.alexa.worldservice.service.CityService;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.shelberg.entity.SearchCity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/api/v1/search/city")
public class SearchCityByCriteriaServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final XmlMapper xmlMapper = ServiceLocator.get(XmlMapper.class);
    private final CityService cityService = ServiceLocator.get(CityService.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String country = request.getParameter("country");
        String name = request.getParameter("name");
        String continent = request.getParameter("continent");
        CitySearchQuery citySearchQuery;
        String fields = request.getParameter("fields");
        if (fields != null) {
            citySearchQuery = new CitySearchQuery.Builder(country, name, continent)
                    .countryRequired(fields.contains("countryName"))
                    .populationRequired(fields.contains("population"))
                    .countryPopulationRequired((fields.contains("countryPopulation"))).build();
        }
        citySearchQuery = new CitySearchQuery.Builder(country, name, continent).build();

        String acceptType = request.getHeaders("Accept").nextElement();

        logger.info("Getting city with parameters:{}", citySearchQuery);

        if (acceptType.contains(MimeType.APPLICATION_XML.getValue())) {
            List<SearchCity> cities = cityService.getCitiesByCriteria(citySearchQuery);
            String xml = xmlMapper.writeValueAsString(cities);
            response.setContentType(MimeType.APPLICATION_XML.getValue());
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().print(xml);
        } else {
            response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }
    }
}