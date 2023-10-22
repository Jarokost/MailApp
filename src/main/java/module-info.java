module com.example.mailapp {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.web;

    opens com.example.mailapp;
    opens com.example.mailapp.view;
    opens com.example.mailapp.controller;
}