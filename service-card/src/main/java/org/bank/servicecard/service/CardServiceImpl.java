package org.bank.servicecard.service;

import lombok.RequiredArgsConstructor;
import org.bank.servicecard.model.dto.CardDto;
import org.bank.servicecard.model.entity.Card;
import org.bank.servicecard.model.status.CardStatus;
import org.bank.servicecard.repository.CardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final ModelMapper modelMapper;

    @Override
    public CardDto registerCard(String email) {
        Card card = new Card();
        card.setCardHolder(email);
        card.setCardStatus(CardStatus.PENDING);
        card.setCardBalance(0);
        cardRepository.save(card);
        return modelMapper.map(card, CardDto.class);
    }

    @Override
    public CardDto getCard(String email) {
        Optional<Card> card = cardRepository.findCardByCardHolder(email);
        return modelMapper.map(card.get(), CardDto.class);
    }
}
