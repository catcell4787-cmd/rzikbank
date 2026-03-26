package org.bank.servicecard.repository;

import org.bank.servicecard.model.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findCardByCardHolder(String cardHolder);
}
