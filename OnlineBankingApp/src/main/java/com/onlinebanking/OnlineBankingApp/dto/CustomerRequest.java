package com.onlinebanking.OnlineBankingApp.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
	@Column(unique = true, nullable = false)
	  private String customerId;
	  
	  @Column(nullable = false)
	  private String firstName;
	  
	  @Column(nullable = false)
	  private String lastName;
	  
	  @Column(unique = true, nullable = false)
	  private String password;
	  
	  @Column(unique = true, nullable = false)
	  private String email;
	  
	  @Column(unique = true, nullable = false)
	  private String phoneNumber;
	  
	  @Column(unique = true, nullable = false)
	  private String adharNumber;
	  
	  @Column(nullable = false)
	  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	  private Date  birthDate;
	  
	  @Column(nullable = false)
	  private String gender;
	  
	  @Column(nullable = false)
	  private String address;
	  
	  @Column(nullable = false)
	  private String city;
	  
	  @Column(nullable = false)
	  private String state;
	  
	  @Column(nullable = false)
	  private String AccountType;
	    
}
