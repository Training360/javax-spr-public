package spring.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ClientLoggerHandlerInterceptor
    implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String client = request.getHeader("User-Agent");
        logger.debug("Client: {}", client);
        return true;
    }
}
