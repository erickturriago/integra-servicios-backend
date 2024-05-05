package com.backend.integraservicios;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
@EnableMethodSecurity
public class IntegraServiciosApplication {

    private static Logger logger = LoggerFactory.getLogger(IntegraServiciosApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(IntegraServiciosApplication.class, args);
        logger.info("Integraservicios is now running...");
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


}
