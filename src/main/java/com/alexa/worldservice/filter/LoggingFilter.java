package com.alexa.worldservice.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@WebFilter(urlPatterns = "/*")
public class LoggingFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) (servletRequest);
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String headerName =  headerNames.nextElement();
            logger.debug("Getting all headers with name: {}, {}", headerName, httpServletRequest.getHeader(headerName));
        }

        Enumeration<String> traceId = httpServletRequest.getHeaders("x-b3-traceid");
        if (traceId.hasMoreElements()) {
            MDC.put("x-b3-traceid",traceId.nextElement());

        }
        Enumeration<String> spanId = httpServletRequest.getHeaders("x-b3-spanid");
        if (spanId.hasMoreElements() ){
            MDC.put("x-b3-spanid", spanId.nextElement());
        }

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            MDC.clear();
            throw new RuntimeException("Unable to process filtering for request: " + servletRequest, e);
        }
        MDC.clear();
    }

    @Override
    public void destroy() {

    }
}
