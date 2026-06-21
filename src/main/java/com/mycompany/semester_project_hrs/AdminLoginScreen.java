package com.mycompany.semester_project_hrs;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class AdminLoginScreen {

    private Stage stage;

    public AdminLoginScreen(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        stage.setTitle("Admin Login");

        
        Label titleLabel = new Label("Admin Login");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        emailField.setPromptText("Enter admin email");
        emailField.setText("admin@hotel.com"); // pre-fill for easy testing

        Label passLabel = new Label("Password:");
        PasswordField passField = new PasswordField();
        passField.setPromptText("Enter password");
        passField.setText("admin123"); // pre-fill for easy testing

        
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

            
            boolean success = MainApp.hotelManager.adminLogin(email, pass);

            if (success) {
                
                AdminDashboard dashboard = new AdminDashboard(stage);
                dashboard.show();
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid email or password.");
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
