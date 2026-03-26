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
@RequestMapping("/clients")
public class ClientController {

    private final AccountService accountService;

    @GetMapping("/list")
    private ResponseEntity<?> listClients() {
        return ResponseEntity.ok(accountService.findByRole(AccountRole.CLIENT));
    }

    @PostMapping("/add")
    private ResponseEntity<?> addClient(@Valid @RequestBody AccountCredentialsDto accountCredentialsDto) {
        return ResponseEntity.ok(
                accountService.register(accountCredentialsDto, AccountRole.CLIENT, true)
        );
    }

    @PostMapping("/{email}/cards/register")
    private ResponseEntity<?> registerCard(@Valid @PathVariable String email) {
        return ResponseEntity.ok(accountService.registerCard(email));
    }

    @GetMapping("/{email}/cards/get")
    private ResponseEntity<?> getCard(@Valid @PathVariable String email) {
        return ResponseEntity.ok(accountService.getCard(email));
    }

    @GetMapping("/{email}")
    private ResponseEntity<?> getClient(@Valid @PathVariable String email) {
        return ResponseEntity.ok(accountService.getClientInfo(email));
    }
}
