package org.bank.serviceaccount.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bank.serviceaccount.model.dto.AccountCredentialsDto;
import org.bank.serviceaccount.model.dto.AccountDto;
import org.bank.serviceaccount.model.role.AccountRole;
import org.bank.serviceaccount.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/hello")
    private ResponseEntity<?> startPage() {
        return ResponseEntity.ok("Rzikbank");
    }

    @PostMapping("/login")
    private ResponseEntity<?> login(@Valid @RequestBody AccountCredentialsDto accountCredentialsDto) {
        return ResponseEntity.ok(accountService.login(accountCredentialsDto));
    }

    @PostMapping("/register")
    private ResponseEntity<?> register(@Valid @RequestBody AccountCredentialsDto accountCredentialsDto) {
        return ResponseEntity.ok(accountService.register(accountCredentialsDto, AccountRole.CLIENT, false));
    }

    @GetMapping("/{email}")
    private ResponseEntity<?> getAccount(@PathVariable String email) {
        return ResponseEntity.ok(accountService.findByEmail(email));
    }

    @PatchMapping("/{email}/update")
    private ResponseEntity<?> updateStatus(@PathVariable String email, @Valid @RequestBody AccountDto accountDto) {
        return ResponseEntity.ok(accountService.updateStatus(email, accountDto));
    }
}
