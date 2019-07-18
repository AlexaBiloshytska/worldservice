package com.alexa.worldservice.servlet;

import com.alexa.worldservice.ServiceLocator;
import com.alexa.worldservice.service.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/api/v1/delete/country")
public class DeleteCountryServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final CountryService countryService = ServiceLocator.get(CountryService.class);


    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");

        countryService.delete(name);
        logger.info("Country is successfully deleted");
    }


}
