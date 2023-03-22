package com.garamgaebi.GaramgaebiServer;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySource;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.TimeZone;

@OpenAPIDefinition(servers = {@Server(url = "/", description = "Server URL")})
@EnableJpaAuditing
@EnableAsync
@SpringBootApplication
@EnableEncryptableProperties
@EncryptablePropertySource(name="EncryptedProperties", value = "classpath:application.properties")
public class GaramgaebiServerApplication {

	@PostConstruct
	public void startedTimeZoneSet() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}

	public static void main(String[] args) {
		SpringApplication.run(GaramgaebiServerApplication.class, args);
	}

}
