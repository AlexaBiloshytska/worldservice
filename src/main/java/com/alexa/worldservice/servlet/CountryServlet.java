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
    private XmlMapper xmlMapper = ServiceLocator.get(XmlMapper.class);
    private final ObjectMapper mapper = ServiceLocator.get(ObjectMapper.class);
    private CountryService countryService = ServiceLocator.get(CountryService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long startTime = System.currentTimeMillis();
        String code = request.getParameter("code");
        String acceptType = request.getHeaders("Accept").nextElement();

        logger.info("Getting country with country name {} ", code);

        if (acceptType.contains(MimeType.APPLICATION_XML.getValue())) {
            try {
                Country country = countryService.getCountry(code);
                String xml = xmlMapper.writerWithView(CountrySearch.class).writeValueAsString(country);
                response.setContentType(MimeType.APPLICATION_XML.getValue());
                response.setStatus(HttpServletResponse.SC_OK);
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(xml);
            } catch (NoDataFoundException e) {
                logger.warn("The country with name: {} is not found", code);
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }
        logger.info("Finished getting country language statistics in {} ms", startTime - System.currentTimeMillis());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String countryJson = getRequestBody(request);

        Country country = mapper.readValue(countryJson, Country.class);

        Country addedCountry = countryService.add(country);
        String json = mapper.writeValueAsString(addedCountry);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MimeType.APPLICATION_JSON.getValue());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
        logger.info("Country is successfully added {}", country);

    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String countryJson = getRequestBody(request);
        Country country = mapper.readValue(countryJson, Country.class);

        Country updatedCountry = countryService.update(country);

        String json = mapper.writeValueAsString(updatedCountry);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MimeType.APPLICATION_JSON.getValue());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);

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
}

