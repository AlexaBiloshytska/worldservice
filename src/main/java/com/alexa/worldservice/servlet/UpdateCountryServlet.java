package com.alexa.worldservice.servlet;

import com.alexa.worldservice.ServiceLocator;
import com.alexa.worldservice.service.CountryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shelberg.entity.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/api/v1/update/country")
public class UpdateCountryServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ObjectMapper objectMapper = ServiceLocator.get(ObjectMapper.class);
    private final CountryService countryService = ServiceLocator.get(CountryService.class);

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cityJson = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Country country = objectMapper.readValue(cityJson, Country.class);

        countryService.update(country);
        logger.info("Country is successfully updated {}", country);
    }

}
