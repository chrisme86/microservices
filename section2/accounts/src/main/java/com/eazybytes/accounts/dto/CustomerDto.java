package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema( // documentation
        name = "Customer",
        description = "Schema to hold Customer and Account information"
)
public class CustomerDto
{
    @Schema(description = "Name of the Customer", example = "Eazy Bytes") // doc
    /*
    Whatever data we receive from clients (other MS) will be transfered into DTOs
    >> make all validations here!
     */
    @NotEmpty(message = "Name cannot be null or empty")
    @Size(min = 5, max = 50, message = "The length of the customer name should be between 5 and 50")
    private String name;

    @Schema(description = "Email of the Customer", example = "tutor@eazybytes.com")
    @Email(message = "Email should be a valid value")
    @NotEmpty(message = "Email cannot be null or empty")
    private String email;

    @Schema(description = "Name of the Customer", example = "1234567890")
    @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Schema(description = "Account details of the Customer") // no example as AccountsDto has own @Schema values
    private AccountsDto accountsDto;
}
