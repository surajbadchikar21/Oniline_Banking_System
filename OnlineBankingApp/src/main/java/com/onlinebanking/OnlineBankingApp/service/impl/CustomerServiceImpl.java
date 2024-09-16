package com.onlinebanking.OnlineBankingApp.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinebanking.OnlineBankingApp.dto.AccountInfo;
import com.onlinebanking.OnlineBankingApp.dto.BankResponse;
import com.onlinebanking.OnlineBankingApp.dto.CreditDebitRequest;
import com.onlinebanking.OnlineBankingApp.dto.CustomerRequest;
import com.onlinebanking.OnlineBankingApp.dto.EmailDetails;
import com.onlinebanking.OnlineBankingApp.dto.EnquiryRequest;
import com.onlinebanking.OnlineBankingApp.dto.TransferRequest;
import com.onlinebanking.OnlineBankingApp.entity.Customer;
import com.onlinebanking.OnlineBankingApp.repository.CustomerRepository;
import com.onlinebanking.OnlineBankingApp.utils.AccountUtils;
import com.onlinebanking.OnlineBankingApp.utils.CustomerIdUtils;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	EmailService emailService;

	@Override
	public BankResponse createAccount(CustomerRequest customerRequest) {
		//creating an account and saving new user into database.
		//check if user already exist or not.
		
		if(customerRepository.existsByEmail(customerRequest.getEmail()))
		{
			return BankResponse.builder()
					.responseCode(AccountUtils.ACCOUNT_EXIST_CODE)
					.responseMessage(AccountUtils.ACCOUNT_EXIST_MESSAGE)
					.accountInfo(null)
					.build();
			
		}
		
		Customer newCustomer=Customer.builder()
				//creating a new Customer using the fields from entity package Customer class.
				.customerId(CustomerIdUtils.generateCustomerId()) //generating random customerId
				.firstName(customerRequest.getFirstName())
				.lastName(customerRequest.getLastName())
				.password(customerRequest.getPassword())
				.email(customerRequest.getEmail())
				.phoneNumber(customerRequest.getPhoneNumber())
				.adharNumber(customerRequest.getAdharNumber())
				.birthDate(customerRequest.getBirthDate())
				.gender(customerRequest.getGender())
				.address(customerRequest.getAddress())
				.city(customerRequest.getCity())
				.state(customerRequest.getState())
				.accountNumber(AccountUtils.generateAccountNumber()) //generating random Account Number
				.AccountType(customerRequest.getAccountType() != null ? customerRequest.getAccountType():"SAVINGS")
				.AccountBalance(BigDecimal.ZERO)
				.status("ACTIVE") //once account number is created the account should be active.
				.build();
		
		   //saving customer into database.
		   Customer saveCustomer=customerRepository.save(newCustomer);
		   //send email to user after creating an account.
		   EmailDetails emailDetails=EmailDetails.builder()
				   .recepient(saveCustomer.getEmail())
				   .subject("Registration and Account Creation")
				   .messageBody("Your Account Is Created Succefully.\n Your Credentials are:\n Account Name: " +saveCustomer.getFirstName()+" "+saveCustomer.getLastName()+
						   "\nAccount Number :"+saveCustomer.getAccountNumber()+
						   "\nAccount Balance :"+saveCustomer.getAccountBalance()+
						   "\nCustomer ID: "+saveCustomer.getCustomerId())
						
				   .build();
		   emailService.sendEmailAlert(emailDetails);
		   
		   return BankResponse.builder()
				   .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
				   .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
				   .accountInfo(AccountInfo.builder()
						   .accountNumber(saveCustomer.getAccountNumber())
						   .accountBalance(saveCustomer.getAccountBalance())
						   
						   .build())
				   .build();
	}
	
	@Override
    public BankResponse createLogin(String customerId, String password) {
        // Check if customer exists by customerId
        Customer existingCustomer = customerRepository.findByCustomerId(customerId);
        if (existingCustomer == null) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.INVALID_CUSTOMER_ID_CODE)
                    .responseMessage(AccountUtils.INVALID_CUSTOMER_ID_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        // Password Validation
        if (!existingCustomer.getPassword().equals(password)) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.INVALID_PASSWORD_CODE)
                    .responseMessage(AccountUtils.INVALID_PASSWORD_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        return BankResponse.builder()
                .responseCode(AccountUtils.LOGIN_SUCCESS_CODE)
                .responseMessage(AccountUtils.LOGIN_SUCCESS_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountNumber(existingCustomer.getAccountNumber())
                        .accountBalance(existingCustomer.getAccountBalance())
                        .build())
                .build();
       
    }

	@Override
	public BankResponse balanceEnquiry(EnquiryRequest request) {
	    // Check if the provided account number exists in the db
	    boolean isAccountExist = customerRepository.existsByAccountNumber(request.getAccountNumber());
	    
	    if (!isAccountExist) {
	        return BankResponse.builder()
	                .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
	                .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
	                .accountInfo(null)
	                .build();
	    }

	    Customer foundCustomer = customerRepository.findByAccountNumber(request.getAccountNumber());
	    
	    return BankResponse.builder()
	            .responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
	            .responseMessage(AccountUtils.ACCOUNT_FOUND_SUCCESS)
	            .accountInfo(AccountInfo.builder()
	                    .accountBalance(foundCustomer.getAccountBalance())
	                    .accountNumber(request.getAccountNumber())
//	                    .accountName(foundCustomer.getFirstName() + " " + foundCustomer.getLastName())
	                    .build())
	            .build();
	}

	@Override
	public String nameEnquiry(EnquiryRequest request) {
	    boolean isAccountExist = customerRepository.existsByAccountNumber(request.getAccountNumber());
	    
	    if (!isAccountExist) {
	        return AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE;
	    }
	    
	    Customer foundCustomer = customerRepository.findByAccountNumber(request.getAccountNumber());
	    
	    return foundCustomer.getFirstName() + " " + foundCustomer.getLastName();
	}

	@Override
	public BankResponse creditAccount(CreditDebitRequest request) {
		 //checking if the account exists
        boolean isAccountExist = customerRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExist){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        Customer userToCredit = customerRepository.findByAccountNumber(request.getAccountNumber());
        userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(request.getAmount()));
        customerRepository.save(userToCredit);

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREDITED_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREDITED_SUCCESS_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountName(userToCredit.getFirstName() + " " + userToCredit.getLastName() + " " + userToCredit.getCustomerId())
                        .accountBalance(userToCredit.getAccountBalance())
                        .accountNumber(request.getAccountNumber())
                        .build())
                .build();
	}

	@Override
	public BankResponse debitAccount(CreditDebitRequest request) {
		 //check if the account exists
        //check if the amount you intend to withdraw is not more than the current account balance
        boolean isAccountExist = customerRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExist){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        Customer userToDebit = customerRepository.findByAccountNumber(request.getAccountNumber());
        BigInteger availableBalance =userToDebit.getAccountBalance().toBigInteger();
        BigInteger debitAmount = request.getAmount().toBigInteger();
        if ( availableBalance.intValue() < debitAmount.intValue()){
            return BankResponse.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        else {
            userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(request.getAmount()));
            customerRepository.save(userToDebit);
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_DEBITED_SUCCESS)
                    .responseMessage(AccountUtils.ACCOUNT_DEBITED_MESSAGE)
                    .accountInfo(AccountInfo.builder()
                            .accountNumber(request.getAccountNumber())
                            .accountName(userToDebit.getFirstName() + " " + userToDebit.getLastName())
                            .accountBalance(userToDebit.getAccountBalance())
                            .build())
                    .build();
        }
	}

	@Override
	public BankResponse transfer(TransferRequest request) {
		
		boolean isDestinationAccountExist = customerRepository.existsByAccountNumber(request.getSourceAccountNumber());
		
		if(!isDestinationAccountExist) {
			 return BankResponse.builder()
	                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
	                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
	                    .accountInfo(null)
	                    .build();
		}
		
		Customer sourceAccountUser= customerRepository.findByAccountNumber(request.getSourceAccountNumber());
		String SourceUsername = sourceAccountUser.getFirstName() +" "+ sourceAccountUser.getLastName();
		
		
		if(request.getAmount().compareTo(sourceAccountUser.getAccountBalance()) > 0) {
			
			return BankResponse.builder()
					 .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
	                    .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
	                    .accountInfo(null)
	                    .build();
		}
		
		sourceAccountUser.setAccountBalance(sourceAccountUser.getAccountBalance().subtract(request.getAmount()));
		customerRepository.save(sourceAccountUser);
		EmailDetails debitAlert= EmailDetails.builder()
				.subject("Debit Alert ")
				.recepient(sourceAccountUser.getEmail())
				.messageBody("The sum of " + request.getAmount()+"has been debited by your account your remaining balance is " +sourceAccountUser.getAccountBalance())
				.build();
		
		emailService.sendEmailAlert(debitAlert);
				

		Customer destinationAccountUser= customerRepository.findByAccountNumber(request.getDestinationAccountNumber());
//		String reciepientUsername = destinationAccountUser.getFirstName()+" "+destinationAccountUser.getLastName();
		destinationAccountUser.setAccountBalance(destinationAccountUser.getAccountBalance().add(request.getAmount()));
		customerRepository.save(destinationAccountUser);
		
		EmailDetails creditAlert= EmailDetails.builder()
				.subject("Credit Alert ")
				.recepient(destinationAccountUser.getEmail())
				.messageBody("The sum of " + request.getAmount()+"has been credited to  your account by " +SourceUsername )
				.build();
		
		emailService.sendEmailAlert(creditAlert);
		
		return BankResponse.builder()
				.responseCode(AccountUtils.TRANSFER_SUCCESSFUL_CODE)
				.responseMessage(AccountUtils.TRANSFER_SUCCESSFUL_MESSAGE)
				.accountInfo(null)
				.build();
				
		
	}
	
		
		
	}

	

