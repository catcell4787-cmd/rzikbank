package org.bank.serviceaccount.service;

import org.bank.serviceaccount.model.dto.AccountCredentialsDto;
import org.bank.serviceaccount.model.dto.AccountDto;
import org.bank.serviceaccount.model.dto.CardDto;
import org.bank.serviceaccount.model.role.AccountRole;

import java.util.List;

public interface AccountService {
    AccountDto register(AccountCredentialsDto accountDto, AccountRole accountRole, boolean status);
    AccountDto login(AccountCredentialsDto accountCredentialsDto);
    AccountDto findByEmail(String email);
    List<AccountDto> findByRole(AccountRole accountRole);
    AccountDto updateStatus(String email, AccountDto accountDto);
    CardDto registerCard(String email);
    CardDto getCard(String email);
}
