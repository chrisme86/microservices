package com.eazybytes.accounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// if this ex is thrown the client will receive a BAD_REQUEST status!
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomerAlreadyExistsException extends RuntimeException
{
    public CustomerAlreadyExistsException(final String message)
    {
        super(message);
    }
}
