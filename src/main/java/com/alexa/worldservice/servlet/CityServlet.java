package com.alexa.worldservice.servlet;

import com.alexa.worldservice.ServiceLocator;
import com.alexa.worldservice.constant.MimeType;
import com.alexa.worldservice.exception.NoDataFoundException;
import com.alexa.worldservice.service.CityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.shelberg.entity.City;
import com.shelberg.entity.Country;
import com.shelberg.entity.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/api/v1/cities")
public class CityServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ObjectMapper objectMapper = ServiceLocator.get(ObjectMapper.class);
    private final XmlMapper xmlMapper = ServiceLocator.get(XmlMapper.class);
    private CityService cityService = ServiceLocator.get(CityService.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long startTime = System.currentTimeMillis();
        int id = request.getIntHeader("id");
        String acceptType = request.getHeaders("Accept").nextElement();

        if (acceptType.contains(MimeType.APPLICATION_XML.getValue())) {
            try {
                City city = cityService.getCityById(id);
                String xml = xmlMapper.writerWithView(City.class).writeValueAsString(city);
                response.setContentType(MimeType.APPLICATION_XML.getValue());
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(xml);

            } catch (NoDataFoundException e) {
                logger.warn("The city with id: {} is not found", id);
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }
        logger.info("Finished getting city with id in {} ms", startTime - System.currentTimeMillis());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        String name = request.getParameter("name");
        String countryCode = request.getParameter("countryCode");
        String district = request.getParameter("district");
        Integer population = request.getIntHeader("population");

        City city = new City();
        city.setName(name);
        city.setDistrict(district);
        city.setCountryCode(countryCode);
        city.setPopulation(population);

        cityService.add(city);
        logger.info("City is successfully added {}", city);


    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        int id = request.getIntHeader("id");

        City city = new City();
        city.setId(id);
        cityService.delete(id);
        logger.info("City with id {} is successfully deleted", id);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String cityJson = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        City city = objectMapper.readValue(cityJson, City.class);

        cityService.update(city);
        logger.info("City is successfully updated {}", city);
    }
}

