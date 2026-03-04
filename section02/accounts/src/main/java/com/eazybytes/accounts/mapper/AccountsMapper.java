package com.eazybytes.accounts.mapper;

import com.eazybytes.accounts.dto.AccountsDto;
import com.eazybytes.accounts.entity.Accounts;

// 3rd party: modelmapper or mapStruct can do it manually >> but not officially recognised by Spring Framework (not in Initializer)
public class AccountsMapper
{

    public static AccountsDto mapToAccountsDto(final Accounts accounts, final AccountsDto accountsDto)
    {
        accountsDto.setAccountNumber(accounts.getAccountNumber());
        accountsDto.setAccountType(accounts.getAccountType());
        accountsDto.setBranchAddress(accounts.getBranchAddress());
        return accountsDto;
    }

    public static Accounts mapToAccounts(final AccountsDto accountsDto, final Accounts accounts)
    {
        accounts.setAccountNumber(accountsDto.getAccountNumber());
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setBranchAddress(accountsDto.getBranchAddress());
        return accounts;
    }

}
