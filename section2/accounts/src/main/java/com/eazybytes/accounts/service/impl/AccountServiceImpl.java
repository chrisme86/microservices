package com.eazybytes.accounts.service.impl;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.eazybytes.accounts.constants.AccountsConstants;
import com.eazybytes.accounts.dto.AccountsDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.exception.CustomerAlreadyExistsException;
import com.eazybytes.accounts.exception.ResourceNotFoundException;
import com.eazybytes.accounts.mapper.AccountsMapper;
import com.eazybytes.accounts.mapper.CustomerMapper;
import com.eazybytes.accounts.repository.AccountsRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import com.eazybytes.accounts.service.IAccountsService;

import lombok.AllArgsConstructor;
import lombok.val;

@Service
@AllArgsConstructor // for DI, don't use @Autowire
public class AccountServiceImpl implements IAccountsService
{

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(final CustomerDto customerDto)
    {
        val customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        val optionalCustomer = this.customerRepository.findByMobileNumber(customer.getMobileNumber());
        if(optionalCustomer.isPresent())
        {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber " + customer.getMobileNumber());
        }

        val savedCustomer = this.customerRepository.save(customer);
        this.accountsRepository.save(createNewAccount(savedCustomer));
    }

    private Accounts createNewAccount(final Customer savedCustomer)
    {
        // the accountId!
        val newAccount = new Accounts();
        final long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setCustomerId(savedCustomer.getCustomerId());

        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);

        return newAccount;
    }

    @Override
    public CustomerDto fetchAccount(final String mobileNumber)
    {

        val customer = this.customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        val account = this.accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));

        val customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(account, new AccountsDto()));
        return customerDto;
    }

    @Override
    public boolean updateAccount(final CustomerDto customerDto)
    {
        boolean isUpdated = false;
        val accountsDto = customerDto.getAccountsDto();
        if(accountsDto != null)
        {
            var accounts = this.accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Acount", "accountNumber", accountsDto.getAccountNumber().toString())
            );
            // accountsDto new value is mapped to accounts
            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accounts = this.accountsRepository.save(accounts);

            val customerId = accounts.getCustomerId();
            var customer = this.customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "customerId", customerId.toString())
            );

            CustomerMapper.mapToCustomer(customerDto, customer);
            // does insert (create) if no id is present, otherwise update
            customer = this.customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(final String mobileNumber)
    {
        val customer = this.customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        this.accountsRepository.deleteByCustomerId(customer.getCustomerId());
        this.customerRepository.delete(customer);

        return true;
    }
}
