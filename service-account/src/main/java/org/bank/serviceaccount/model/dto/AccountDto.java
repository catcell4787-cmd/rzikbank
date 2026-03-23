package org.bank.serviceaccount.model.dto;

import lombok.Data;

@Data
public class AccountDto {
    private String email;
    private String role;
    private boolean enabled;
}
