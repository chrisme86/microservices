package com.eazybytes.cards.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@ConfigurationProperties(prefix = "build")
public final class BuildInfoDto
{
    private String version;
}
