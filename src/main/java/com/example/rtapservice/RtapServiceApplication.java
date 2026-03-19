package com.example.rtapservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RtapServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(RtapServiceApplication.class, args);
        System.out.println("RTAP Service is running...");
    }

}
