module com.example.mailapp {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.web;
    requires activation;
    requires javax.mail.api;
    requires java.desktop;

    opens com.example.mailapp;
    opens com.example.mailapp.view;
    opens com.example.mailapp.controller;
    opens com.example.mailapp.model;
}