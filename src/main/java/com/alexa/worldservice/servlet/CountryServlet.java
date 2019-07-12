package com.alexa.worldservice.servlet;

import com.alexa.worldservice.ServiceLocator;
import com.alexa.worldservice.service.CountryService;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.shelberg.entity.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;


@WebServlet(urlPatterns = "/api/v1/countries")
public class CountryServlet extends HttpServlet {
    private final XmlMapper xmlMapper = new XmlMapper();
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private CountryService countryService = ServiceLocator.get(CountryService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long startTime = System.currentTimeMillis();
        String countryName = request.getParameter("name");
        Enumeration<String> accept = request.getHeaders("Accept");
        String acceptFirst = accept.nextElement();

        logger.info("Getting country with country name {} ", countryName);

        String contentType = request.getContentType();
        if (contentType == null || contentType.equals(acceptFirst)) {
            Country country = countryService.getCountry(countryName);
            String xml = xmlMapper.writeValueAsString(country);
            response.getWriter().print(xml);
        } else {
            response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }

        logger.info("Finished getting country language statistics in {} ms", startTime - System.currentTimeMillis());
    }

}
