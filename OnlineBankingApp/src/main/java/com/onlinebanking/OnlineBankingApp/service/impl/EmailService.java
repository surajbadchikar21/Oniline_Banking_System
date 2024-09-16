package com.onlinebanking.OnlineBankingApp.service.impl;

import com.onlinebanking.OnlineBankingApp.dto.EmailDetails;

public interface EmailService {
   void sendEmailAlert(EmailDetails emailDetails);
}
