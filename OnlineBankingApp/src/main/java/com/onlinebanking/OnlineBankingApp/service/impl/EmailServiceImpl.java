package com.onlinebanking.OnlineBankingApp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.onlinebanking.OnlineBankingApp.dto.EmailDetails;

@Service
public class EmailServiceImpl implements EmailService{

	@Autowired
	private JavaMailSender javamailsender;
	
	@Value("${spring.mail.username}")
	private String senderEmail;
	@Override
	public void sendEmailAlert(EmailDetails emailDetails) {
		try {
			SimpleMailMessage mailMessage=new SimpleMailMessage();
			mailMessage.setFrom(senderEmail);
			mailMessage.setTo(emailDetails.getRecepient());
			mailMessage.setText(emailDetails.getMessageBody());
			mailMessage.setSubject(emailDetails.getSubject());
			
			javamailsender.send(mailMessage);
			System.out.println("Email Sent Successfully");
		}catch(MailException e) {
			throw new RuntimeException(e);
		}
		
	}

}
