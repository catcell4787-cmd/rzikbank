package org.bank.serviceaccount.repository;

import org.bank.serviceaccount.model.dto.AccountDto;
import org.bank.serviceaccount.model.entity.Account;
import org.bank.serviceaccount.model.role.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Account> findByRole(AccountRole role);
}
