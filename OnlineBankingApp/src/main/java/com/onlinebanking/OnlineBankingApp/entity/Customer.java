package com.onlinebanking.OnlineBankingApp.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="Customer")
public class Customer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(name = "customer_id", unique = true, nullable = false)
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
  
  @Column(nullable = true, unique = true)
  private String accountNumber;
  
  @Column(nullable = false)
  private String AccountType;
  
  @Column(nullable = true )
  private BigDecimal AccountBalance;
  
  @Column(nullable = true )
  private String status;
  
  @CreationTimestamp
  @Column(nullable = false)
  private LocalDateTime createdAt;
  
  @UpdateTimestamp
  @Column(nullable = false)
  private LocalDateTime modifiedAt;
}
