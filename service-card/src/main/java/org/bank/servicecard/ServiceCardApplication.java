package org.bank.servicecard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceCardApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceCardApplication.class, args);
    }

}
