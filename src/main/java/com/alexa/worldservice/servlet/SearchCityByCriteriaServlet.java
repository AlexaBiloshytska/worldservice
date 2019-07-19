package com.alexa.worldservice.servlet;

import com.alexa.worldservice.ServiceLocator;
import com.alexa.worldservice.constant.MimeType;
import com.alexa.worldservice.entity.SearchCity;
import com.alexa.worldservice.service.CityService;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
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
    private final XmlMapper xmlMapper = new XmlMapper();
    private final CityService cityService = ServiceLocator.get(CityService.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String country = request.getParameter("country");
        String cityName = request.getParameter("name");
        String continent = request.getParameter("continent");

        String fields = request.getParameter("fields");

        boolean countryRequired = fields.contains("countryName");
        boolean populationRequired = fields.contains("population");
        boolean countryPopulationRequired = fields.contains("countryPopulation");

        String acceptType = request.getHeaders("Accept").nextElement();

        logger.info("Getting city with parameters:{},{},{},{},{},{} ", countryRequired, populationRequired,
                countryPopulationRequired,
                country, cityName, continent);

        if (acceptType.contains(MimeType.APPLICATION_XML.getValue())) {
            List<SearchCity> cities = cityService.getCitiesByCriteria(countryRequired, populationRequired, countryPopulationRequired, country, cityName, continent);
            String xml = xmlMapper.writeValueAsString(cities);
            response.setContentType(MimeType.APPLICATION_XML.getValue());
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().print(xml);
        }


    }


}
