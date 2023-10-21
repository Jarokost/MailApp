module com.example.mailapp {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;

    opens com.example.mailapp;
    opens com.example.mailapp.view;
}