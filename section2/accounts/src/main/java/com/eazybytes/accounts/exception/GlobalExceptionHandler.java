package com.eazybytes.accounts.exception;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.eazybytes.accounts.dto.ErrorResponseDto;

import lombok.val;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler
{

    // Make validation exceptions better readable
    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatusCode status, final WebRequest request)
    {
        val validationErrors = new HashMap<String, String>();
        val validationErrorList = ex.getBindingResult().getAllErrors();
        validationErrorList.forEach(error -> {
            val name = ((FieldError) error).getField();
            val value = error.getDefaultMessage(); // validation exc message
            validationErrors.put(name, value);
        });
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(final Exception exception, final WebRequest webRequest)
    {
        final ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
                webRequest.getDescription(false), // lots of details
                HttpStatus.INTERNAL_SERVER_ERROR, // RuntimeException (and not handled checked Exc)
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomerAlreadyExistsException.class) // this method handles CustomerAlreadyExistsException
    public ResponseEntity<ErrorResponseDto> handleCustomerAlreadyExistsException(final CustomerAlreadyExistsException exception,
            final WebRequest webRequest)
    {
        final ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
                webRequest.getDescription(false), // lots of details
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(final ResourceNotFoundException exception,
            final WebRequest webRequest)
    {
        final ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
                webRequest.getDescription(false), // lots of details
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }
}
