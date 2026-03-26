package org.bank.serviceaccount.model.dto;

import lombok.Data;

@Data
public class ClientDto {
    private String email;
    private String role;
    private boolean enabled;
    private CardDto card;
}
