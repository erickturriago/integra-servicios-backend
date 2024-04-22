package com.backend.integraservicios;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class IntegraServiciosApplication {

    private static Logger logger = LoggerFactory.getLogger(IntegraServiciosApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(IntegraServiciosApplication.class, args);
        logger.info("ClinicaOdontologica is now running...");
    }


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


}
