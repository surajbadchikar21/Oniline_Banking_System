package com.onlinebanking.OnlineBankingApp.service.impl;

import com.onlinebanking.OnlineBankingApp.dto.BankResponse;
import com.onlinebanking.OnlineBankingApp.dto.CreditDebitRequest;
import com.onlinebanking.OnlineBankingApp.dto.CustomerRequest;
import com.onlinebanking.OnlineBankingApp.dto.EnquiryRequest;
import com.onlinebanking.OnlineBankingApp.dto.TransferRequest;

public interface CustomerService {
	BankResponse createAccount(CustomerRequest customerRequest);
	BankResponse createLogin(String CustomerId, String password); 
	
	BankResponse balanceEnquiry(EnquiryRequest request);
	
	String nameEnquiry(EnquiryRequest request);
	
	BankResponse creditAccount(CreditDebitRequest request);
	
	BankResponse debitAccount(CreditDebitRequest request);
	
	BankResponse transfer(TransferRequest request);
}
