package com.alexa.worldservice.servlet;

import com.alexa.worldservice.ServiceLocator;
import com.alexa.worldservice.entity.CountryLanguageStatistics;
import com.alexa.worldservice.service.CountryService;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = "/api/v1/countries")
public class CountryServlet extends HttpServlet {
    private final XmlMapper xmlMapper = new XmlMapper();
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private CountryService countryService = ServiceLocator.get(CountryService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        long startTime = System.currentTimeMillis();

        String countryName = request.getParameter("name");


        logger.info("Getting country with country name {} ", countryName);

        CountryLanguageStatistics getCountryByName = countryService.getStatistics(countryName);

        String contentType = request.getContentType();
        if (contentType == null || contentType.equals("text/xml")) {
            String xml = xmlMapper.writeValueAsString(getCountryByName);
            response.getWriter().print(xml);
        } else {
            response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }

        logger.info("Finished getting country language statistics in {} ms", startTime - System.currentTimeMillis());
    }

}