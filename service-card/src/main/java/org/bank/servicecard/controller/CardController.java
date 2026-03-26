package org.bank.servicecard.controller;

import lombok.RequiredArgsConstructor;
import org.bank.servicecard.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clients")
public class CardController {

    private final CardService cardService;

    @PostMapping("/{email}/cards/register")
    private ResponseEntity<?> registerCard(@PathVariable String email) {
        return ResponseEntity.ok(cardService.registerCard(email));
    }

    @GetMapping("/{email}/cards/get")
    private ResponseEntity<?> getCard(@PathVariable String email) {
        return ResponseEntity.ok(cardService.getCard(email));
    }
}
