package org.bank.servicecard.service;

import org.bank.servicecard.model.dto.CardDto;

public interface CardService {
    CardDto registerCard(String email);
    CardDto getCard(String email);
}
