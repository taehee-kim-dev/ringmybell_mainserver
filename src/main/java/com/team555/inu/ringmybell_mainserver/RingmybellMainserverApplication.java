package com.team555.inu.ringmybell_mainserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = {"classpath:${jdbc.config}"})
public class RingmybellMainserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(RingmybellMainserverApplication.class, args);
    }

}
