package org.bank.serviceaccount.model.dto;

import lombok.Data;

@Data
public class AccountAuthorizationTokenDto {
    private String token;
    private String refreshToken;
}
