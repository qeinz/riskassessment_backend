package de.minesort.riskAssessment.configuration;

import de.minesort.riskAssessment.interceptor.RequestLoggerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * JavaDoc this file!
 * Created: 11.05.2024
 *
 * @author Nikk (dominik@minesort.de)
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestLoggerInterceptor());
    }
}