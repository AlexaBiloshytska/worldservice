package com.alexa.worldservice.servlet;

import com.alexa.worldservice.ServiceLocator;
import com.alexa.worldservice.service.CountryService;
import com.fasterxml.jackson.annotation.JsonView;
import com.shelberg.entity.Country;
import com.shelberg.entity.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.MimeType;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@JsonView(Views.CountryData.class)
@WebServlet(urlPatterns = "/api/v1/language")
public class CountriesByLanguageServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private CountryService countryService = ServiceLocator.get(CountryService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String language = request.getParameter("language");

        logger.info("Getting country with language {} ", language);


        if (MimeType.APPLICATION_XML.getValue().equals(acceptType)) {
            List<Country> countries = countryService.getCountriesByLanguage(language);

            String xml = xmlMapper.writeValueAsString(countries);
            response.getWriter().print(xml);
        } else {
            response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }

        logger.info("Finished getting country language statistics in {} ms", startTime - System.currentTimeMillis());
    }
    }


    }