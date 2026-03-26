package org.bank.serviceaccount.rest;

import org.bank.serviceaccount.model.dto.CardDto;
import org.bank.serviceaccount.rest.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "service-card", configuration =  FeignConfig.class)
public interface CardFeignClient {

    @GetMapping("clients/{email}/cards/get")
    CardDto get(@PathVariable String email);

    @PostMapping("clients/{email}/cards/register")
    CardDto registerCard(@PathVariable String email);
}
