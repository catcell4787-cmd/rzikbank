package org.bank.serviceaccount.utils;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.bank.serviceaccount.model.entity.AccountEntity;
import org.bank.serviceaccount.model.role.AccountRole;
import org.bank.serviceaccount.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AdminCreator {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${admin.email}")
    private String adminEmail;
    @Value("${admin.password}")
    private String adminPassword;

    @PostConstruct
    public void createAdmin() {
        if (!accountRepository.existsByEmail(adminEmail)) {
            AccountEntity account = new AccountEntity();
            account.setEmail(adminEmail);
            account.setPassword(passwordEncoder.encode(adminPassword));
            account.setEnabled(true);
            account.setRole(AccountRole.ADMIN);
            accountRepository.save(account);
        }
    }
}
