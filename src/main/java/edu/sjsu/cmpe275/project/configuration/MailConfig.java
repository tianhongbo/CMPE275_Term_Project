package edu.sjsu.cmpe275.project.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Project Name: CMPE275_Term_Project
 * Packet Name: edu.sjsu.cmpe275.project.configuration
 * Author: Scott
 * Created Date: 11/18/15 5:03 PM
 * Copyright (c) 2015, 2015 All Right Reserved, http://sjsu.edu/
 * This source is subject to the GPL2 Permissive License.
 * Please see the License.txt file for more information.
 * All other rights reserved.
 * <p>
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY
 * KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
 * PARTICULAR PURPOSE.
 */
@Configuration
public class MailConfig {

    /*
    @Value("${email.host}")
    private String host;

    @Value("${email.port}")
    private Integer port;


    @Value("${email.username}")
    private String username;

    @Value("${email.password}")
    private String password;
*/
    //TODO move to configuration file
    private String host = "smtp.gmail.com";
    private Integer port = 587;
    private String username = "cmpe275.mini.hotel@gmail.com";
    private String password = "cmpe275test";
    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);

        javaMailSender.setJavaMailProperties(getMailProperties());

        return javaMailSender;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.starttls.required", "true");
        properties.setProperty("mail.smtp.EnableSSL.enable","true");
        properties.setProperty("mail.debug", "true");
        return properties;
    }
}