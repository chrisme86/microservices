package com.eazybytes.cards.dto;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cards")
public record CardsContactInfoDto(String message, ContactDetailsInfoDto contactDetails, List<String> onCallSupport) { }
