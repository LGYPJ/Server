package com.garamgaebi.GaramgaebiServer;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableJpaRepositories
@EnableWebMvc
public class AppConfig {
}
