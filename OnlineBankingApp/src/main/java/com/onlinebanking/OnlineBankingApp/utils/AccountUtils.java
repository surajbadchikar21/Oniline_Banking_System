package com.onlinebanking.OnlineBankingApp.utils;

import java.time.Year;

public class AccountUtils {
	
	public static final String ACCOUNT_EXIST_CODE="001";
	public static final String ACCOUNT_EXIST_MESSAGE="This Customer Already Exists..";
	
	public static final String ACCOUNT_CREATION_SUCCESS="002";
	public static final String ACCOUNT_CREATION_MESSAGE="Account Created Sucessfully.";
	
	public static final String INVALID_CUSTOMER_ID_CODE = "404";
    public static final String INVALID_CUSTOMER_ID_MESSAGE = "Customer ID Not Found.";
    
    public static final String INVALID_PASSWORD_CODE = "401";
    public static final String INVALID_PASSWORD_MESSAGE = "Invalid password.";
    
    public static final String LOGIN_SUCCESS_CODE = "200";
    public static final String LOGIN_SUCCESS_MESSAGE = "Login successful.";
    public static final String ACCOUNT_NOT_EXIST_CODE = "003";
    public static final String ACCOUNT_NOT_EXIST_MESSAGE = "User with the provided Account Number does not exist";
    public static final String ACCOUNT_FOUND_CODE = "004";
    public static final String ACCOUNT_FOUND_SUCCESS = "User Account Found";
    public static final String ACCOUNT_CREDITED_SUCCESS = "005";
    public static final String ACCOUNT_CREDITED_SUCCESS_MESSAGE = "User Account was credited successfully";
    public static final String INSUFFICIENT_BALANCE_CODE = "006";
    public static final String INSUFFICIENT_BALANCE_MESSAGE = "Insufficient Balance";
    public static final String ACCOUNT_DEBITED_SUCCESS = "007";
    public static final String ACCOUNT_DEBITED_MESSAGE = "Account has been successfully debited";
	public static final String TRANSFER_SUCCESSFUL_CODE = "008";
	public static final String TRANSFER_SUCCESSFUL_MESSAGE = "Transfer Successful";

	
	public static String generateAccountNumber()
	{
		/*
		 AccountNumber should start with current yea i.e. 2024 and it should have random 8 digits.
		 2024 + randoEightDigits..	  
		 */
		
		Year currentYear=Year.now(); //it stores the current year.
		
		int min=10000000;  //minimum 8 digit number.
		int max=99999999; //maximum 8 digit number.
		
		//generate a random account number between min and max.
		
		int accountNumber=(int)Math.floor(Math.random()*(max-min +1)+min);
		
		//converting the current year and generated account number into string and concatinate them.
		String year=String.valueOf(currentYear);
		
		String randomAccountNumber=String.valueOf(accountNumber);
		
		StringBuilder generatedAccountNumber=new StringBuilder();
		
		return generatedAccountNumber.append(year).append(randomAccountNumber).toString();
	}
	
}
