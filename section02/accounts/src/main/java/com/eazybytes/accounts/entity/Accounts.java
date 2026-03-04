package com.eazybytes.accounts.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class Accounts extends BaseEntity
{
    @Id
    // no automation here, it's logic inside rest controller
    private Long accountNumber;
    // @Column(name = "customer_id") // not needed
    private Long customerId;
    private String accountType;
    private String branchAddress;
}
