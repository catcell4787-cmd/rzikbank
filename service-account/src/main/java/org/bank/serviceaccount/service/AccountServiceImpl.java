package org.bank.serviceaccount.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bank.serviceaccount.exception.GlobalExceptionHandler;
import org.bank.serviceaccount.model.dto.AccountCredentialsDto;
import org.bank.serviceaccount.model.dto.AccountDto;
import org.bank.serviceaccount.model.dto.CardDto;
import org.bank.serviceaccount.model.dto.ClientDto;
import org.bank.serviceaccount.model.entity.Account;
import org.bank.serviceaccount.model.role.AccountRole;
import org.bank.serviceaccount.repository.AccountRepository;
import org.bank.serviceaccount.rest.CardFeignClient;
import org.bank.serviceaccount.security.jwt.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    private final CardFeignClient cardFeignClient;

    @Override
    public AccountDto register(
            AccountCredentialsDto accountCredentialsDto,
            AccountRole accountRole,
            boolean status) {
        if (accountRepository.existsByEmail(accountCredentialsDto.getEmail())) {
            throw new GlobalExceptionHandler.ConflictException("Email already exists");
        }
        Account account = modelMapper.map(accountCredentialsDto, Account.class);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setEnabled(status);
        account.setRole(accountRole);
        accountRepository.save(account);
        AccountDto accountDto = modelMapper.map(account, AccountDto.class);
        log.info("Account {} has been created", account.getId());
        return accountDto;
    }

    @Override
    public List<AccountDto> findByRole(AccountRole accountRole) {
        List<Account> accounts = accountRepository.findByRole(accountRole);
        return accounts.stream()
                .map(account -> modelMapper.map(account, AccountDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto updateStatus(String email, AccountDto accountDto) {
        Optional<Account> optionalAccount = accountRepository.findByEmail(email);
        if (optionalAccount.isEmpty()) {
            throw new GlobalExceptionHandler.ResourceNotFoundException("Account not found");
        }
        Account account = optionalAccount.get();
        account.setEnabled(accountDto.isEnabled());
        accountRepository.save(account);
        return modelMapper.map(accountDto, AccountDto.class);
    }

    @Override
    public CardDto registerCard(String email) {
        if (accountRepository.existsByEmail(email)) {
            if (cardFeignClient.getCard(email) == null) {
                return cardFeignClient.registerCard(email);
            }
            throw new GlobalExceptionHandler.ConflictException("Card already exists");
        }
        throw new GlobalExceptionHandler.ResourceNotFoundException("Account is not registered");
    }

    @Override
    public CardDto getCard(String email) {
        if (!accountRepository.existsByEmail(email)) {
            throw new GlobalExceptionHandler.ResourceNotFoundException("Account not found");
        }
        return cardFeignClient.getCard(email);
    }

    @Override
    public ClientDto getClientInfo(String email) {
        Optional<Account> optionalAccount = accountRepository.findByEmail(email);
        if (optionalAccount.isPresent()) {
            try {
                Account account = optionalAccount.get();
                ClientDto clientDto = modelMapper.map(account, ClientDto.class);
                CardDto cardDto = cardFeignClient.getCard(email);
                clientDto.setCard(cardDto);
                return clientDto;
            } catch (FeignException e) {
                throw new GlobalExceptionHandler.ResourceNotFoundException("Card is not registered");
            }
        }
        throw new GlobalExceptionHandler.ResourceNotFoundException("Account is not registered");
    }

    @Override
    public AccountDto login(AccountCredentialsDto accountCredentialsDto) {
        Optional<Account> optionalAccount = accountRepository.findByEmail(accountCredentialsDto.getEmail());
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            if (!account.isEnabled()) {
                throw new GlobalExceptionHandler.AccountStatusException("Account is not enabled");
            }
            if (passwordEncoder.matches(accountCredentialsDto.getPassword(), account.getPassword())) {
                String token = jwtService.getRefreshToken(account.getEmail());
                System.out.println(token);
                return modelMapper.map(account, AccountDto.class);
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
