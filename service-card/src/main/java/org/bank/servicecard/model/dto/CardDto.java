package org.bank.servicecard.model.dto;

import lombok.Data;
import org.bank.servicecard.model.status.CardStatus;

@Data
public class CardDto {

    private String cardHolder;
    private String cardNumber;
    private String cardBalance;
    private CardStatus cardStatus;

}
