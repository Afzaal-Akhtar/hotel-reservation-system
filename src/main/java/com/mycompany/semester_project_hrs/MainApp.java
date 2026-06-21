package com.mycompany.semester_project_hrs;

import javafx.application.Application;
import javafx.stage.Stage;


public class MainApp extends Application {

    public static HotelManager hotelManager;

    @Override
    public void start(Stage primaryStage) {
        hotelManager = new HotelManager();

        
        LoginScreen loginScreen = new LoginScreen(primaryStage);
        loginScreen.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
