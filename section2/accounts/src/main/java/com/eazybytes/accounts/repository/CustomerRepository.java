package com.eazybytes.accounts.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eazybytes.accounts.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>
{
    // SpringDataJpa generates automatically correct impl! (field aaa in Customer must match findByAaa)
    Optional<Customer> findByMobileNumber(String mobileNumber);
}
