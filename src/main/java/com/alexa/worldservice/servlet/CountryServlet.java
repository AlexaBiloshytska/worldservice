package com.alexa.worldservice.servlet;

import com.alexa.worldservice.ServiceLocator;
import com.alexa.worldservice.constant.MimeType;
import com.alexa.worldservice.exception.NoDataFoundException;
import com.alexa.worldservice.service.CountryService;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.shelberg.entity.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.shelberg.entity.Views.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/api/v1/countries")
public class CountryServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private XmlMapper xmlMapper = ServiceLocator.get(XmlMapper.class);
    private CountryService countryService = ServiceLocator.get(CountryService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long startTime = System.currentTimeMillis();
        String countryName = request.getParameter("name");
        String acceptType = request.getHeaders("Accept").nextElement();

        logger.info("Getting country with country name {} ", countryName);

        if (acceptType.contains(MimeType.APPLICATION_XML.getValue())) {
            try {
                Country country = countryService.getCountry(countryName);
                String xml = xmlMapper.writerWithView(CountryStatistic.class).writeValueAsString(country);
                response.setContentType(MimeType.APPLICATION_XML.getValue());
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(xml);
            } catch (NoDataFoundException e) {
                logger.warn("The country with name: {} is not found", countryName);
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }
        logger.info("Finished getting country language statistics in {} ms", startTime - System.currentTimeMillis());
    }
}

