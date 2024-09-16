package com.onlinebanking.OnlineBankingApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onlinebanking.OnlineBankingApp.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
   //method to check whether the customer is already exist or not.
	Boolean existsByEmail(String email);
    Customer findByCustomerId(String customerId);
    Boolean existsByAccountNumber(String accountNumber);
    Customer findByAccountNumber(String accountNumber);
}
