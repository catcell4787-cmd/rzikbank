package org.bank.serviceaccount.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bank.serviceaccount.model.dto.AccountCredentialsDto;
import org.bank.serviceaccount.model.role.AccountRole;
import org.bank.serviceaccount.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/managers")
public class ManagerController {
    private final AccountService accountService;

    @GetMapping("/list")
    ResponseEntity<?> listManagers() {
        return ResponseEntity.ok(accountService.findByRole(AccountRole.MANAGER));
    }

    @PostMapping("/hire")
    public ResponseEntity<?> hireManager(@Valid @RequestBody AccountCredentialsDto accountCredentialsDto) {
        return ResponseEntity.ok(accountService.register(accountCredentialsDto, AccountRole.MANAGER, true));
    }
}
