package com.trungntm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DevlifeEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevlifeEurekaServerApplication.class, args);
    }

}
