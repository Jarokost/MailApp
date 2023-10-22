package com.example.mailapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view/MainWindow.fxml")));
        Scene scene = new Scene(parent);
        stage.setTitle("MailApp");
        stage.setScene(scene);
        stage.show();
    }
}