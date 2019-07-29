package com.alexa.worldservice.servlet;

import com.alexa.worldservice.ServiceLocator;
import com.alexa.worldservice.constant.MimeType;
import com.alexa.worldservice.exception.NoDataFoundException;
import com.alexa.worldservice.service.CountryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.shelberg.entity.Country;
import com.shelberg.entity.Views.CountrySearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;


@WebServlet(urlPatterns = "/api/v1/countries")
public class CountryServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ObjectMapper mapper = ServiceLocator.get(ObjectMapper.class);
    private CountryService countryService = ServiceLocator.get(CountryService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long startTime = System.currentTimeMillis();
        String code = request.getParameter("code");

        logger.info("Getting country with country code {} ", code);
        try {
            Country country = countryService.getByCode(code);
            String responseJson = mapper.writerWithView(CountrySearch.class).writeValueAsString(country);
            processResponse(response, responseJson);
            logger.info("Finished getting country: {} in {} ms", country, startTime - System.currentTimeMillis());
        } catch (NoDataFoundException e) {
            logger.warn("The country with code: {} is not found", code);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String countryJson = getRequestBody(request);
        Country country = mapper.readValue(countryJson, Country.class);
        Country addedCountry = countryService.add(country);

        String responseJson = mapper.writerWithView(CountrySearch.class).writeValueAsString(addedCountry);
        processResponse(response, responseJson);

        logger.info("Country is successfully added {}", country);
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String countryJson = getRequestBody(request);
        Country country = mapper.readValue(countryJson, Country.class);
        Country updatedCountry = countryService.update(country);

        String responseJson = mapper.writerWithView(CountrySearch.class).writeValueAsString(updatedCountry);
        processResponse(response, responseJson);
        logger.info("Country is successfully updated {}", country);
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code = request.getParameter("code");

        int affectedRows = countryService.delete(code);
        if (affectedRows == 0) {
            logger.warn("Country with code: {} was not found", code);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            logger.info("Country with code: {} is successfully deleted", code);
            response.setStatus(HttpServletResponse.SC_OK);
        }
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

