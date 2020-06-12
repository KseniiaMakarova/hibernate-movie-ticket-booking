package com.booking.tickets.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.booking.tickets.entity",
        "com.booking.tickets.exception.controller"})
public class WebConfig {
}
