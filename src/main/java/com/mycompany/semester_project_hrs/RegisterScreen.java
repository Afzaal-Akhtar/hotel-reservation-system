package com.mycompany.semester_project_hrs;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class RegisterScreen {

    private Stage stage;

    public RegisterScreen(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        stage.setTitle("Register - New Customer");

        
        Label titleLabel = new Label("Register New Account");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        
        Label nameLabel = new Label("Full Name:");
        TextField nameField = new TextField();
        nameField.setPromptText("Enter your name");

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");

        Label passLabel = new Label("Password:");
        PasswordField passField = new PasswordField();
        passField.setPromptText("Create a password");

        Label phoneLabel = new Label("Phone:");
        TextField phoneField = new TextField();
        phoneField.setPromptText("Enter phone number");

        
        Button registerBtn = new Button("Register");
        registerBtn.setPrefWidth(130);

        Button backBtn = new Button("Back");
        backBtn.setPrefWidth(130);

       
        registerBtn.setOnAction(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String pass = passField.getText().trim();
            String phone = phoneField.getText().trim();

            
            if (name.isEmpty() || email.isEmpty() || pass.isEmpty() || phone.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Missing Fields", "Please fill in all fields.");
                return;
            }

            
            Customer newCustomer = new Customer(name, email, pass, phone);
            boolean success = MainApp.hotelManager.registerCustomer(newCustomer);

            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success",
                        "Registration successful!\nYou can now login with your email and password.");
                LoginScreen loginScreen = new LoginScreen(stage);
                loginScreen.show();
            } else {
                showAlert(Alert.AlertType.ERROR, "Email Taken",
                        "This email is already registered. Please use a different email.");
            }
        });

        
        backBtn.setOnAction(e -> {
            LoginScreen loginScreen = new LoginScreen(stage);
            loginScreen.show();
        });

        HBox buttonRow = new HBox(10, registerBtn, backBtn);
        buttonRow.setAlignment(Pos.CENTER);

        
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(12);
        form.setAlignment(Pos.CENTER);
        form.add(nameLabel, 0, 0);
        form.add(nameField, 1, 0);
        form.add(emailLabel, 0, 1);
        form.add(emailField, 1, 1);
        form.add(passLabel, 0, 2);
        form.add(passField, 1, 2);
        form.add(phoneLabel, 0, 3);
        form.add(phoneField, 1, 3);

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        layout.getChildren().addAll(titleLabel, form, buttonRow);

        Scene scene = new Scene(layout, 400, 320);
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
