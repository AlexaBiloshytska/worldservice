package com.alexa.worldservice.servlet;

import com.alexa.worldservice.ServiceLocator;
import com.alexa.worldservice.constant.MimeType;
import com.alexa.worldservice.exception.NoDataFoundException;
import com.alexa.worldservice.service.CityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.shelberg.entity.City;
import com.shelberg.entity.Country;
import com.shelberg.entity.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/api/v1/cities")
public class CityServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ObjectMapper objectMapper = ServiceLocator.get(ObjectMapper.class);
    private final XmlMapper xmlMapper = ServiceLocator.get(XmlMapper.class);
    private CityService cityService = ServiceLocator.get(CityService.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long startTime = System.currentTimeMillis();

        int id = Integer.parseInt(request.getParameter("id"));
        String acceptType = request.getHeaders("Accept").nextElement();

        if (acceptType.contains(MimeType.APPLICATION_XML.getValue())) {
            try {
                City city = cityService.getCityById(id);
                String xml = xmlMapper.writerWithView(City.class).writeValueAsString(city);
                response.setContentType(MimeType.APPLICATION_JSON.getValue());
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(xml);

            } catch (NoDataFoundException e) {
                logger.warn("The city with id: {} is not found", id);
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }
        logger.info("Finished getting city with id in {} ms", startTime - System.currentTimeMillis());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String cityJson = getRequestBody(request);
        City city = objectMapper.readValue(cityJson, City.class);

        cityService.add(city);
        logger.info("City is successfully added {}", city);

        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));

        cityService.delete(id);
        logger.info("City with id {} is successfully deleted", id);

        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String cityJson = getRequestBody(request);
        City city = objectMapper.readValue(cityJson, City.class);

        cityService.update(city);
        logger.info("City is successfully updated {}", city);

        response.setStatus(HttpServletResponse.SC_OK);
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

