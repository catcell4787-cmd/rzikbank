package org.bank.serviceaccount.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bank.serviceaccount.exception.GlobalExceptionHandler;
import org.bank.serviceaccount.model.dto.AccountCredentialsDto;
import org.bank.serviceaccount.model.entity.AccountEntity;
import org.bank.serviceaccount.model.role.AccountRole;
import org.bank.serviceaccount.repository.AccountRepository;
import org.bank.serviceaccount.security.jwt.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        AccountEntity account = modelMapper.map(accountCredentialsDto, AccountEntity.class);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setEnabled(status);
        account.setRole(accountRole);
        accountRepository.save(account);
        return ResponseEntity.ok("Account created successfully");
    }

    @Override
    public ResponseEntity<?> login(AccountCredentialsDto accountCredentialsDto) {
        Optional<AccountEntity> optionalAccount = accountRepository.findByEmail(accountCredentialsDto.getEmail());
        if (optionalAccount.isPresent()) {
            AccountEntity account = optionalAccount.get();
            if (!account.isEnabled()) {
                throw new GlobalExceptionHandler.AccountStatusException("Account is not enabled");
            }
            if (passwordEncoder.matches(accountCredentialsDto.getPassword(), account.getPassword())) {
//                log.info("tokens: {}", jwtService.generateAuthToken(account.getEmail()));
                String token = jwtService.getRefreshToken(account.getEmail());
                System.out.println(token);
                return ResponseEntity.ok("Logged in successfully");
            } else {
                throw new GlobalExceptionHandler.AuthenticationException("Invalid password");
            }
        }
        throw new GlobalExceptionHandler.AuthenticationException("Email is not registered");
    }
}
