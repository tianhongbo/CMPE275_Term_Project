package edu.sjsu.cmpe275.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
//@Configuration
//@ComponentScan
//@EnableAutoConfiguration
//@EnableWebMvc
@EnableAsync
public class MiniHotelApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniHotelApplication.class, args);
    }
}
