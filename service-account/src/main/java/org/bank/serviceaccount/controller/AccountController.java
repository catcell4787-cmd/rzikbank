package org.bank.serviceaccount.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bank.serviceaccount.model.dto.AccountCredentialsDto;
import org.bank.serviceaccount.model.role.AccountRole;
import org.bank.serviceaccount.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AccountCredentialsDto accountCredentialsDto) throws AuthenticationException {
        return ResponseEntity.ok(accountService.login(accountCredentialsDto));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AccountCredentialsDto accountCredentialsDto) {
        return ResponseEntity.ok(accountService.register(accountCredentialsDto, AccountRole.CLIENT, false));
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getAccount(@PathVariable("email") String email) {
        return ResponseEntity.ok(accountService.findByEmail(email));
    }
}
