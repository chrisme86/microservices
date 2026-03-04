package com.eazybytes.accounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException
{
    public ResourceNotFoundException(final String resourceName, final String fieldName, final String fieldValue)
    {
        super("%s not found with the given input data %s: '%s'".formatted(resourceName, fieldName, fieldValue));
    }
}
