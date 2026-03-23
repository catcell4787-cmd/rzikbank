package org.bank.serviceaccount.service;

import org.bank.serviceaccount.model.dto.AccountCredentialsDto;
import org.bank.serviceaccount.model.role.AccountRole;
import org.springframework.http.ResponseEntity;

public interface AccountService {
    ResponseEntity<?> register(AccountCredentialsDto accountDto, AccountRole accountRole, boolean status);

    ResponseEntity<?> login(AccountCredentialsDto accountCredentialsDto);
}
