package com.onlinebanking.OnlineBankingApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlinebanking.OnlineBankingApp.dto.BankResponse;
import com.onlinebanking.OnlineBankingApp.dto.CreditDebitRequest;
import com.onlinebanking.OnlineBankingApp.dto.CustomerRequest;
import com.onlinebanking.OnlineBankingApp.dto.EnquiryRequest;
import com.onlinebanking.OnlineBankingApp.dto.TransferRequest;
import com.onlinebanking.OnlineBankingApp.service.impl.CustomerService;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

	@Autowired
	CustomerService customerService;
	
	@PostMapping("/createAccount")
	public BankResponse createAccount(@RequestBody CustomerRequest customerRequest)
	{
		return customerService.createAccount(customerRequest);
	}
	
	@GetMapping("/login/{customerId}/{password}")
    public BankResponse login(@PathVariable("customerId") String customerId,@PathVariable("password") String password) {
        BankResponse response = customerService.createLogin(customerId, password);
        return response;
    }
	
	
	   @GetMapping("balanceEnquiry")
	    public BankResponse balanceEnquiry(@RequestBody EnquiryRequest request){
	        return customerService.balanceEnquiry(request);
	    }

	    @GetMapping("nameEnquiry")
	    public String nameEnquiry(@RequestBody EnquiryRequest request){
	        return customerService.nameEnquiry(request);
	    }
	    
	    @PostMapping("credit")
	    public BankResponse creditAccount(@RequestBody CreditDebitRequest request){
	        return customerService.creditAccount(request);
	    }

	    @PostMapping("debit")
	    public BankResponse debitAccount(@RequestBody CreditDebitRequest request){
	        return customerService.debitAccount(request);
	    }
	    
	    @PostMapping("transfer")
	    public BankResponse transfer(@RequestBody TransferRequest request) {
	    	return customerService.transfer(request);
	    }
}
