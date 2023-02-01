package com.garamgaebi.GaramgaebiServer.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("classpath:application.properties")
public class MailConfig {

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String id;

    @Value("${spring.mail.password}")
    private String password;

    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setJavaMailProperties(getMailProperties());
        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setUsername(id);
        javaMailSender.setPassword(password);

        return javaMailSender;
    }

    private Properties getMailProperties() {
        Properties pt = new Properties();

        pt.put("mail.smtp.starttls.enable", "true");
        pt.setProperty("spring.mail.properties.mail.smtp.auth", "true");
        pt.setProperty("spring.mail.properties.mail.transport.protocol", "smtp");

        return pt;
    }
}

