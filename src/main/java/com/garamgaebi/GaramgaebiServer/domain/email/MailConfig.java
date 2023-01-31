package com.garamgaebi.GaramgaebiServer.domain.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;
/*
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

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private boolean auth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private boolean starttls;

//    @Value("${spring.mail.properties.mail.smtp.starttls.required}")
//    private boolean starttls_required;

//    @Value("${spring.mail.properties.mail.transport.protocol}")
//    private String protocol;

    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setUsername(id);
        javaMailSender.setPassword(password);
        javaMailSender.setJavaMailProperties(getMailProperties());
//        javaMailSender.setDefaultEncoding("UTF-8");

        return javaMailSender;
    }

    private Properties getMailProperties() {
        Properties pt = new Properties();

        pt.setProperty("spring.mail.properties.mail.smtp.starttls.enable", "true");
        pt.setProperty("spring.mail.properties.mail.smtp.auth", "true");

        // before put

//        pt.put("spring.mail.properties.mail.smtp.starttls.required", starttls_required);
//        pt.put("spring.mail.properties.mail.transport.protocol", protocol);

        return pt;
    }
}

 */
