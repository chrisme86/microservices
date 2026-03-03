package com.eazybytes.loans.service.impl;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.eazybytes.loans.constants.LoansConstants;
import com.eazybytes.loans.dto.LoansDto;
import com.eazybytes.loans.entity.Loans;
import com.eazybytes.loans.exception.LoanAlreadyExistsException;
import com.eazybytes.loans.exception.ResourceNotFoundException;
import com.eazybytes.loans.mapper.LoansMapper;
import com.eazybytes.loans.repository.LoansRepository;
import com.eazybytes.loans.service.ILoansService;

import lombok.AllArgsConstructor;
import lombok.val;

@Service
@AllArgsConstructor
public class LoansServiceImpl implements ILoansService
{
    private final LoansRepository loansRepository;

    @Override
    public void createLoan(final String mobileNumber)
    {
        this.loansRepository.findByMobileNumber(mobileNumber).ifPresent(
                loans -> { throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber " + mobileNumber); }
        );
        this.loansRepository.save(createNewLoan(mobileNumber));
    }

    private Loans createNewLoan(final String mobileNumber)
    {
        val newLoan = new Loans();
        newLoan.setMobileNumber(mobileNumber);
        // exactly 14 digits [10e12, 100e12)
        newLoan.setLoanNumber(String.valueOf(ThreadLocalRandom.current().nextLong(10_000_000_000_000L, 100_000_000_000_000L)));
        newLoan.setLoanType(LoansConstants.HOME_LOAN);

        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setOutstandingAmount(newLoan.getTotalLoan());
        // newLoan.setAmountPaid(0);
        return newLoan;
    }

    @Override
    public LoansDto fetchLoan(final String mobileNumber)
    {
        val loan = this.loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loans", "mobileNumber", mobileNumber)
        );
        return LoansMapper.mapToLoansDto(loan, new LoansDto());
    }

    @Override
    public boolean updateLoan(final LoansDto loansDto)
    {
        val loan = this.loansRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "loanNumber", loansDto.getLoanNumber())
        );
        LoansMapper.mapToLoans(loansDto, loan);
        this.loansRepository.save(loan);
        return true;
    }

    @Override
    public boolean deleteLoan(final String mobileNumber)
    {
        val loan = this.loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );
        this.loansRepository.delete(loan);
        return true;
    }
}
