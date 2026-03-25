package org.bank.serviceaccount.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bank.serviceaccount.exception.GlobalExceptionHandler;
import org.bank.serviceaccount.model.dto.AccountCredentialsDto;
import org.bank.serviceaccount.model.dto.AccountDto;
import org.bank.serviceaccount.model.entity.Account;
import org.bank.serviceaccount.model.role.AccountRole;
import org.bank.serviceaccount.repository.AccountRepository;
import org.bank.serviceaccount.security.jwt.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<?> register(AccountCredentialsDto accountCredentialsDto, AccountRole accountRole, boolean status) {
        if (accountRepository.existsByEmail(accountCredentialsDto.getEmail())) {
            throw new GlobalExceptionHandler.ConflictException("Email already exists");
        }
        Account account = modelMapper.map(accountCredentialsDto, Account.class);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setEnabled(status);
        account.setRole(accountRole);
        accountRepository.save(account);
        return ResponseEntity.ok("Account created successfully");
    }

    @Override
    public List<AccountDto> findByRole(AccountRole accountRole) {
        List<Account> accounts = accountRepository.findByRole(accountRole);
        List<AccountDto> dtos = accounts.stream().map(account -> modelMapper.map(account, AccountDto.class)).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public ResponseEntity<?> login(AccountCredentialsDto accountCredentialsDto) {
        Optional<Account> optionalAccount = accountRepository.findByEmail(accountCredentialsDto.getEmail());
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            if (!account.isEnabled()) {
                throw new GlobalExceptionHandler.AccountStatusException("Account is not enabled");
            }
            if (passwordEncoder.matches(accountCredentialsDto.getPassword(), account.getPassword())) {
                String token = jwtService.getRefreshToken(account.getEmail());
                System.out.println(token);
                return ResponseEntity.ok("Logged in successfully");
            } else {
                throw new GlobalExceptionHandler.AuthenticationException("Invalid password");
            }
        }
        throw new GlobalExceptionHandler.AuthenticationException("Email is not registered");
    }

    @Override
    public AccountDto findByEmail(String email) {
        Optional<Account> optionalAccount = accountRepository.findByEmail(email);
        if (optionalAccount.isPresent()) {
            return modelMapper.map(optionalAccount.get(), AccountDto.class);
        }
        throw new GlobalExceptionHandler.ResourceNotFoundException("Email is not registered");
    }
}
