module com.example.mailapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.mailapp to javafx.fxml;
    exports com.example.mailapp;
}