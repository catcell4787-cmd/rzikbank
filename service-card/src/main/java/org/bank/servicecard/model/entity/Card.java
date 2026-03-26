package org.bank.servicecard.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.bank.servicecard.model.status.CardStatus;
import org.bank.servicecard.utils.CardNumberGenerator;

@Entity
@Data
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "card_balance")
    private double cardBalance;

    @Column(name = "card_status")
    @Enumerated(EnumType.STRING)
    private CardStatus cardStatus;

    @Column(name = "card_holder")
    private String cardHolder;

    @Column(name = "card_number")
    private String cardNumber;

    @PrePersist
    @PreUpdate
    private void generateCardNumber() {
        CardNumberGenerator bankCardNumberGenerator = new CardNumberGenerator();
        cardNumber = bankCardNumberGenerator.generateBankCardNumber("4");
    }
}
