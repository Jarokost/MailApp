package com.example.mailapp;

import com.example.mailapp.controller.persistance.PersistanceAccess;
import com.example.mailapp.controller.persistance.ValidAccount;
import com.example.mailapp.controller.services.LoginService;
import com.example.mailapp.model.EmailAccount;
import com.example.mailapp.model.EmailMessage;
import com.example.mailapp.view.ViewFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch();
    }
    private PersistanceAccess persistanceAccess = new PersistanceAccess();
    private EmailManager emailManager = new EmailManager();

    @Override
    public void start(Stage stage) throws IOException {

        ViewFactory viewFactory = new ViewFactory(emailManager);
        List<ValidAccount> validAccountList = persistanceAccess.loadFromPersistance();
        if(!validAccountList.isEmpty()) {
            viewFactory.showMainWindow();
            for(ValidAccount validAccount: validAccountList){
                EmailAccount emailAccount = new EmailAccount(validAccount.getAddress(), validAccount.getPassword());
                LoginService loginService = new LoginService(emailAccount, emailManager);
                loginService.start();
            }
        } else {
            viewFactory.showLoginWindow();
        }
    }

    @Override
    public void stop() throws Exception {
        List<ValidAccount> validAccountList = new ArrayList<ValidAccount>();
        for(EmailAccount emailAccount: emailManager.getEmailAccounts()){
            validAccountList.add(new ValidAccount(emailAccount.getAddress(), emailAccount.getPassword()));
        }
        persistanceAccess.saveToPersistance(validAccountList);
    }
}