package com.eazybytes.accounts.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.eazybytes.accounts.entity.Accounts;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long>
{
    Optional<Accounts> findByCustomerId(Long customerId);

    @Transactional // if error inside transaction >> rollback to state before transaction
    @Modifying // this method will modify data: "please execute the query of this meth inside a transaction" >> @Transactional also needed
    void deleteByCustomerId(Long customerId);

}
