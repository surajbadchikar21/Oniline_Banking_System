package com.onlinebanking.OnlineBankingApp.utils;

import java.time.Year;

public class CustomerIdUtils {
	public static String generateCustomerId()
	{
		/*
		 CustomerId should start with current year i.e. 2024 and it should have random 6 digits.
		 2024 + randomSixDigits..	  
		 */
		
		Year currentYear=Year.now(); //it stores the current year.
		
		int min=100000;  //minimum 6 digit number.
		int max=999999; //maximum 6 digit number.
		
		//generate a random account number between min and max.
		
		int customerId=(int)Math.floor(Math.random()*(max-min +1)+min);
		
		//converting the current year and generated account number into string and concatinate them.
		String year=String.valueOf(currentYear);
		
		String randomCustomerId=String.valueOf(customerId);
		
		StringBuilder generatedCustomerId=new StringBuilder();
		
		return generatedCustomerId.append(year).append(randomCustomerId).toString();
	}
}
