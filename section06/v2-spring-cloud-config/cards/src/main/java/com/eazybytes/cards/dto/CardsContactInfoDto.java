package com.eazybytes.cards.dto;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@ConfigurationProperties(prefix = "cards")
public final class CardsContactInfoDto
{
    private String message;
    private ContactDetailsInfoDto contactDetails;
    private List<String> onCallSupport;
}
