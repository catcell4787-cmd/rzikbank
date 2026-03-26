package org.bank.servicecard.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CardConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
