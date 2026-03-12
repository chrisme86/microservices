package com.eazybytes.loans.dto;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "loans")
public record LoansContactInfoDto(String message, ContactDetailsInfoDto contactDetails, List<String> onCallSupport) { }
