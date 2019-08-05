package com.alexa.worldservice.servlet;

import com.alexa.worldservice.ServiceLocator;
import com.alexa.worldservice.constant.MimeType;
import com.alexa.worldservice.exception.NoDataFoundException;
import com.alexa.worldservice.service.CityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shelberg.entity.City;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(urlPatterns = "/api/v1/cities")
public class CityServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ObjectMapper objectMapper = ServiceLocator.get(ObjectMapper.class);
    private CityService cityService = ServiceLocator.get(CityService.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long startTime = System.currentTimeMillis();
        int id = Integer.parseInt(request.getParameter("id"));

        try {
            City city = cityService.getCityById(id);
            String responseBody = objectMapper.writerWithView(City.class).writeValueAsString(city);
            processResponse(response, responseBody);
            logger.info("Finished getting city: {} in {} ms", city, startTime - System.currentTimeMillis());
        } catch (NoDataFoundException e) {
            logger.warn("The city with id: {} is not found", id);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String cityJson = getRequestBody(request);
        City city = objectMapper.readValue(cityJson, City.class);

        City addedCity = cityService.add(city);
        logger.info("City is successfully added {}", city);

        String responseBody = objectMapper.writeValueAsString(addedCity);
        processResponse(response, responseBody);

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        cityService.delete(id);
        response.setStatus(HttpServletResponse.SC_OK);
        logger.info("City with id {} is successfully deleted", id);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String cityJson = getRequestBody(request);
        City city = objectMapper.readValue(cityJson, City.class);

        City updatedCity = cityService.update(city);
        logger.info("City is successfully updated {}", city);

        String responseBody = objectMapper.writeValueAsString(updatedCity);
        processResponse(response, responseBody);
    }

    private String getRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        return buffer.toString();
    }

    private void processResponse(HttpServletResponse response, String responseBody) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MimeType.APPLICATION_JSON.getValue());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseBody);
    }
}

