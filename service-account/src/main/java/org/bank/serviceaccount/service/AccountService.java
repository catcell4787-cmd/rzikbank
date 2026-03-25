package org.bank.serviceaccount.service;

import org.bank.serviceaccount.model.dto.AccountCredentialsDto;
import org.bank.serviceaccount.model.dto.AccountDto;
import org.bank.serviceaccount.model.role.AccountRole;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AccountService {
    ResponseEntity<?> register(AccountCredentialsDto accountDto, AccountRole accountRole, boolean status);
    ResponseEntity<?> login(AccountCredentialsDto accountCredentialsDto);
    AccountDto findByEmail(String email);
    List<AccountDto> findByRole(AccountRole accountRole);
    ResponseEntity<?> updateStatus(String email, AccountDto accountDto);
}
