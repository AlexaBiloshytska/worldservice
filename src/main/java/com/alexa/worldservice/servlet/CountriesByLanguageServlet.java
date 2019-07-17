package com.alexa.worldservice.servlet;

import com.alexa.worldservice.ServiceLocator;
import com.alexa.worldservice.constant.MimeType;
import com.alexa.worldservice.service.CountryService;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.List;

@WebServlet(urlPatterns = "/api/v1/language")
public class CountriesByLanguageServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ObjectMapper mapper = ServiceLocator.get(ObjectMapper.class);
    private final CountryService countryService = ServiceLocator.get(CountryService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long startTime = System.currentTimeMillis();
        String language = request.getParameter("language");

        logger.info("Getting country with language {} ", language);
        String acceptType = request.getHeader("Accept");

        if (acceptType.contains(MimeType.APPLICATION_XML.getValue())) {
            List<Country> countries = countryService.getCountriesByLanguage(language);
            String json = mapper.writerWithView(CountryData.class).writeValueAsString(countries);
            response.setContentType(MimeType.APPLICATION_JSON.getValue());
            response.getWriter().print(json);
        } else {
            response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }
        logger.info("Finished getting countries by language {},{} ", language, startTime - System.currentTimeMillis());
    }
}
