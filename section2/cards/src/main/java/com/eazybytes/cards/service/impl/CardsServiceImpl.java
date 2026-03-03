package com.eazybytes.cards.service.impl;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.eazybytes.cards.constants.CardsConstants;
import com.eazybytes.cards.dto.CardsDto;
import com.eazybytes.cards.entity.Cards;
import com.eazybytes.cards.exception.CardAlreadyExistsException;
import com.eazybytes.cards.exception.ResourceNotFoundException;
import com.eazybytes.cards.mapper.CardsMapper;
import com.eazybytes.cards.repository.CardsRepository;
import com.eazybytes.cards.service.ICardsService;

import lombok.AllArgsConstructor;
import lombok.val;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements ICardsService
{
    private final CardsRepository cardsRepository;

    @Override
    public void createCard(final String mobileNumber)
    {
        this.cardsRepository.findByMobileNumber(mobileNumber).ifPresent(
                c -> { throw new CardAlreadyExistsException("Card already registered with given mobileNumber " + mobileNumber); }
        );
        this.cardsRepository.save(createNewCard(mobileNumber));
    }

    private Cards createNewCard(final String mobileNumber)
    {
        // the id will be set automatically because of @GeneratedValue(strategy = GenerationType.IDENTITY)
        val newCard = new Cards();

        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        // exactly 12 digits [100e9, 1e12)
        newCard.setCardNumber(String.valueOf(ThreadLocalRandom.current().nextLong(100_000_000_000L, 1_000_000_000_000L)));

        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        // newCard.setAmountUsed(0); // not needed as for int: default=0

        return newCard;
    }

    @Override
    public CardsDto fetchCard(final String mobileNumber)
    {
        val card = this.cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));
        return CardsMapper.mapToCardsDto(card, new CardsDto());
    }

    @Override
    public boolean updateCard(final CardsDto cardsDto)
    {
        val card = this.cardsRepository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Card", "cardNumber", cardsDto.getCardNumber()));
        CardsMapper.mapToCards(cardsDto, card); // write to card
        this.cardsRepository.save(card);
        return true;
    }

    @Override
    public boolean deleteCard(final String mobileNumber)
    {
        val card = this.cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));
        this.cardsRepository.delete(card);
        return true;
    }

}
