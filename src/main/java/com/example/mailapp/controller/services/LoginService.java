package com.example.mailapp.controller.services;

import com.example.mailapp.EmailManager;
import com.example.mailapp.model.EmailAccount;

public class LoginService {

    EmailAccount emailAccount;
    EmailManager emailManager;

    public LoginService(EmailAccount emailAccount, EmailManager emailManager) {
        this.emailAccount = emailAccount;
        this.emailManager = emailManager;
    }

    public EmailLoginResult login() {
        return EmailLoginResult.SUCCESS;
    }
}
