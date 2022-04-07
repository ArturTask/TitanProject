package ru.itmo.titan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class TitanApplication {

    public static void main(String[] args) {
        SpringApplication.run(TitanApplication.class, args);
    }

}
