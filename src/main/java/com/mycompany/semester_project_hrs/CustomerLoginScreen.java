package com.mycompany.semester_project_hrs;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class CustomerLoginScreen {

    private Stage stage;

    public CustomerLoginScreen(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        stage.setTitle("Customer Login");

        
        Label titleLabel = new Label("Customer Login");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");

        Label passLabel = new Label("Password:");
        PasswordField passField = new PasswordField();
        passField.setPromptText("Enter your password");

        
        Button loginBtn = new Button("Login");
        loginBtn.setPrefWidth(120);

        Button backBtn = new Button("Back");
        backBtn.setPrefWidth(120);

       
        loginBtn.setOnAction(e -> {
            String email = emailField.getText().trim();
            String pass = passField.getText().trim();

            
            if (email.isEmpty() || pass.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Missing Info", "Please enter email and password.");
                return;
            }

            
            Customer customer = MainApp.hotelManager.customerLogin(email, pass);

            if (customer != null) {
               
                CustomerDashboard dashboard = new CustomerDashboard(stage, customer);
                dashboard.show();
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid email or password.\nPlease register if you are new.");
            }
        });

        
        backBtn.setOnAction(e -> {
            LoginScreen loginScreen = new LoginScreen(stage);
            loginScreen.show();
        });

        HBox buttonRow = new HBox(10, loginBtn, backBtn);
        buttonRow.setAlignment(Pos.CENTER);

        
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(12);
        form.setAlignment(Pos.CENTER);
        form.add(emailLabel, 0, 0);
        form.add(emailField, 1, 0);
        form.add(passLabel, 0, 1);
        form.add(passField, 1, 1);

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        layout.getChildren().addAll(titleLabel, form, buttonRow);

        Scene scene = new Scene(layout, 380, 250);
        stage.setScene(scene);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
