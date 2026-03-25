package com.eazybytes.loans.dto;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@ConfigurationProperties(prefix = "loans")
public final class LoansContactInfoDto
{
    private String message;
    private ContactDetailsInfoDto contactDetails;
    private List<String> onCallSupport;
}
