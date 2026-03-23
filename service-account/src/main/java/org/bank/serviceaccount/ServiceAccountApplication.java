package org.bank.serviceaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceAccountApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceAccountApplication.class, args);
    }
}
