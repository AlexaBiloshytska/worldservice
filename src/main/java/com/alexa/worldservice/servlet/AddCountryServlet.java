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

@WebServlet(urlPatterns = "/api/v1/add/country")
public class AddCountryServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ObjectMapper mapper = ServiceLocator.get(ObjectMapper.class);
    private final CountryService countryService = ServiceLocator.get(CountryService.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Country country = new Country();
        country.setCode(request.getParameter("code"));
        country.setName(request.getParameter("name"));
        country.setContinent(request.getParameter("continent"));
        country.setRegion(request.getParameter("region"));
        country.setSurfaceArea(Double.parseDouble(request.getParameter("surfacaarea")));
        country.setIndepYear(Integer.parseInt(request.getParameter("indepyear")));
        country.setPopulation(Long.parseLong(request.getParameter("population")));
        country.setLifeExpectancy(Double.parseDouble(request.getParameter("lifeexpectancy")));
        country.setGovernmentForm(request.getParameter("governmentform"));
        country.setHeadOfState(request.getParameter("headofstate"));
        country.setCapital(request.getParameter("capital"));

        countryService.add(country);
    }

}
