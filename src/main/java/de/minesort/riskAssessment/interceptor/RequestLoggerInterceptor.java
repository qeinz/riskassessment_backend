package de.minesort.riskAssessment.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * JavaDoc this file!
 * Created: 14.05.2024
 *
 * @author Nikk (dominik@minesort.de)
 */
public class RequestLoggerInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {
        String responseStatusCode = String.valueOf(response.getStatus());
        if (responseStatusCode.startsWith("4")) {
            logger.warn(" ");
            logger.warn("==========> REQUEST-COMPLETION START <==========");
            logger.warn("Incoming Remote IP: {}", request.getRemoteAddr());
            logger.warn("Incoming Local IP: {}", request.getLocalAddr());
            logger.warn("Request URL: {}", request.getRequestURL());
            logger.warn("Response Status: {}", response.getStatus());
            logger.warn("Method: {}", request.getMethod());
            logger.warn("Header companyUuid: {}", request.getHeader("companyUuid"));
            logger.warn("Header secToken: {}", request.getHeader("secToken"));
            logger.warn("Header assignmentUuid: {}", request.getHeader("assignmentUuid"));
            logger.warn("Header additionalAssignmentInfosUuid: {}", request.
                    getHeader("additionalAssignmentInfosUuid"));
            logger.warn("==========> REQUEST-COMPLETION END <==========");
            logger.warn(" ");
        } else if (responseStatusCode.startsWith("5")) {
            logger.error(" ");
            logger.error("==========> REQUEST-COMPLETION START <==========");
            logger.error("Incoming Remote IP: {}", request.getRemoteAddr());
            logger.error("Incoming Local IP: {}", request.getLocalAddr());
            logger.error("Request URL: {}", request.getRequestURL());
            logger.error("Response Status: {}", response.getStatus());
            logger.error("Method: {}", request.getMethod());
            logger.error("Header companyUuid: {}", request.getHeader("companyUuid"));
            logger.error("Header secToken: {}", request.getHeader("secToken"));
            logger.error("Header assignmentUuid: {}", request.getHeader("assignmentUuid"));
            logger.error("Header additionalAssignmentInfosUuid: {}", request.
                    getHeader("additionalAssignmentInfosUuid"));
            logger.error("==========> REQUEST-COMPLETION END <==========");
            logger.error(" ");
        } else {
            logger.info(" ");
            logger.info("==========> REQUEST-COMPLETION START <==========");
            logger.info("Incoming Remote IP: {}", request.getRemoteAddr());
            logger.info("Incoming Local IP: {}", request.getLocalAddr());
            logger.info("Request URL: {}", request.getRequestURL());
            logger.info("Response Status: {}", response.getStatus());
            logger.info("Method: {}", request.getMethod());
            logger.info("Header companyUuid: {}", request.getHeader("companyUuid"));
            logger.info("Header secToken: {}", request.getHeader("secToken"));
            logger.info("Header assignmentUuid: {}", request.getHeader("assignmentUuid"));
            logger.info("Header additionalAssignmentInfosUuid: {}", request.
                    getHeader("additionalAssignmentInfosUuid"));
            logger.info("==========> REQUEST-COMPLETION END <==========");
            logger.info(" ");
        }
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}