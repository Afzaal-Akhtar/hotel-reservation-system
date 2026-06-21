package com.mycompany.semester_project_hrs;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class LoginScreen {

    private Stage stage;

    public LoginScreen(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        stage.setTitle("Hotel Reservation System - Welcome");

       
        Label titleLabel = new Label("Hotel Reservation System");
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Label subLabel = new Label("Please choose how you want to login:");
        subLabel.setStyle("-fx-font-size: 14px;");

       
        Button adminBtn = new Button("Login as Admin");
        adminBtn.setPrefWidth(200);
        adminBtn.setStyle("-fx-font-size: 13px;");

        
        Button customerBtn = new Button("Login as Customer");
        customerBtn.setPrefWidth(200);
        customerBtn.setStyle("-fx-font-size: 13px;");

        
        Button registerBtn = new Button("Register as New Customer");
        registerBtn.setPrefWidth(200);
        registerBtn.setStyle("-fx-font-size: 13px;");

        
        adminBtn.setOnAction(e -> {
            AdminLoginScreen adminLogin = new AdminLoginScreen(stage);
            adminLogin.show();
        });

        
        customerBtn.setOnAction(e -> {
            CustomerLoginScreen customerLogin = new CustomerLoginScreen(stage);
            customerLogin.show();
        });

        
        registerBtn.setOnAction(e -> {
            RegisterScreen registerScreen = new RegisterScreen(stage);
            registerScreen.show();
        });

        
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40));
        layout.getChildren().addAll(titleLabel, subLabel, adminBtn, customerBtn, registerBtn);

        Scene scene = new Scene(layout, 420, 320);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
