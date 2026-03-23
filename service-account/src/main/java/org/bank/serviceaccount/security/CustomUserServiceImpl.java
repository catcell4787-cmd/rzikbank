package org.bank.serviceaccount.security;

import lombok.RequiredArgsConstructor;
import org.bank.serviceaccount.exception.GlobalExceptionHandler;
import org.bank.serviceaccount.model.entity.AccountEntity;
import org.bank.serviceaccount.repository.AccountRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserServiceImpl implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AccountEntity> optionalAccount = accountRepository.findByEmail(username);
        if (optionalAccount.isPresent()) {
            AccountEntity account = optionalAccount.get();
            if (!account.isEnabled()) {
                throw new GlobalExceptionHandler.AccountStatusException("Account is not enabled");
            }
            String role = account.getRole().toString();
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(role));
            return new User(account.getEmail(), account.getPassword(), authorities);
        }
        throw new UsernameNotFoundException(username);
    }
}
