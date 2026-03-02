package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema( // documentation
        name = "Accounts",
        description = "Schema to hold Account information"
)
public class AccountsDto
{
    @Schema(description = "Account Number of Eazy Bank account", example = "1122334455")
    @NotEmpty(message = "Account number must not be null or empty")
    @Pattern(regexp = "^\\d{10}$", message = "Account number must be 10 digits")
    private Long accountNumber;

    @Schema(description = "Account Type of Eazy Bank account", example = "savings")
    @NotEmpty(message = "Account type must not be null or empty")
    private String accountType;

    @Schema(description = "Eazy Bank branch address", example = "123 Dean Street, New York")
    @NotEmpty(message = "Branch address must not be null or empty")
    private String branchAddress;
}
