package org.guestu.webfluxreactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class WebfluxReactiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebfluxReactiveApplication.class, args);
    }

}
