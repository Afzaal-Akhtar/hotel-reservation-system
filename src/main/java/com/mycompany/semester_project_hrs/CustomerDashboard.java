package com.mycompany.semester_project_hrs;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;


public class CustomerDashboard {

    private Stage stage;
    private Customer customer; 

    public CustomerDashboard(Stage stage, Customer customer) {
        this.stage = stage;
        this.customer = customer;
    }

    public void show() {
        stage.setTitle("Customer Dashboard - " + customer.getName());

        
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab bookTab = new Tab("Book a Room", buildBookTab());
        Tab myResTab = new Tab("My Reservations", buildMyReservationsTab());
        Tab serviceTab = new Tab("Room Service", buildServiceTab());
        Tab searchTab = new Tab("Search Rooms", buildSearchTab());

        tabPane.getTabs().addAll(bookTab, myResTab, serviceTab, searchTab);

        
        Label welcomeLabel = new Label("Welcome, " + customer.getName() + "  |  ID: " + customer.getCustomerId());
        welcomeLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;");

        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> {
            LoginScreen loginScreen = new LoginScreen(stage);
            loginScreen.show();
        });

        HBox topBar = new HBox(10, welcomeLabel, logoutBtn);
        topBar.setAlignment(Pos.CENTER_RIGHT);
        topBar.setPadding(new Insets(8, 10, 8, 10));
        topBar.setStyle("-fx-background-color: #dce8f5;");

        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(tabPane);

        Scene scene = new Scene(root, 720, 530);
        stage.setScene(scene);
        stage.setResizable(true);
    }

    
    private VBox buildBookTab() {
        VBox layout = new VBox(14);
        layout.setPadding(new Insets(20));

        Label headingLabel = new Label("Book a Room");
        headingLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

     
        Label roomLabel = new Label("Room Number:");
        TextField roomNumField = new TextField();
        roomNumField.setPromptText("e.g. 101");
        roomNumField.setPrefWidth(130);

        
        Label checkInLabel = new Label("Check-In Date:");
        DatePicker checkInPicker = new DatePicker(LocalDate.now());

      
        Label checkOutLabel = new Label("Check-Out Date:");
        DatePicker checkOutPicker = new DatePicker(LocalDate.now().plusDays(1));

       
        Button bookBtn = new Button("Book Room");
        bookBtn.setPrefWidth(130);

       
        Label resultLabel = new Label("");
        resultLabel.setStyle("-fx-font-size: 13px;");

        bookBtn.setOnAction(e -> {
            String roomText = roomNumField.getText().trim();
            LocalDate checkIn = checkInPicker.getValue();
            LocalDate checkOut = checkOutPicker.getValue();

           
            if (roomText.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Missing Input", "Please enter a room number.");
                return;
            }
            if (checkIn == null || checkOut == null) {
                showAlert(Alert.AlertType.WARNING, "Missing Dates", "Please select check-in and check-out dates.");
                return;
            }

            try {
                int roomNum = Integer.parseInt(roomText);
                Reservation res = MainApp.hotelManager.bookRoom(customer, roomNum, checkIn, checkOut);
                showAlert(Alert.AlertType.INFORMATION, "Booking Confirmed",
                        "Booking successful!\nReservation ID: " + res.getReservationId()
                        + "\nRoom: " + roomNum
                        + "\nCheck-In: " + checkIn
                        + "\nCheck-Out: " + checkOut);
                roomNumField.clear();
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Invalid", "Room number must be a number.");
            } catch (ReservationException | InvalidRoomException ex) {
                showAlert(Alert.AlertType.ERROR, "Booking Failed", ex.getMessage());
            }
        });

       
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(12);
        form.add(roomLabel, 0, 0);
        form.add(roomNumField, 1, 0);
        form.add(checkInLabel, 0, 1);
        form.add(checkInPicker, 1, 1);
        form.add(checkOutLabel, 0, 2);
        form.add(checkOutPicker, 1, 2);

        layout.getChildren().addAll(headingLabel, form, bookBtn, resultLabel);
        return layout;
    }

    
    private VBox buildMyReservationsTab() {
        VBox layout = new VBox(12);
        layout.setPadding(new Insets(15));

        Label headingLabel = new Label("My Reservations");
        headingLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

        
        TableView<Reservation> resTable = new TableView<>();
        resTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        resTable.setPrefHeight(280);

        TableColumn<Reservation, Integer> idCol = new TableColumn<>("Res. ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("reservationId"));

        TableColumn<Reservation, String> roomCol = new TableColumn<>("Room No.");
        roomCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        String.valueOf(data.getValue().getRoom().getRoomNumber())));

        TableColumn<Reservation, String> typeCol = new TableColumn<>("Room Type");
        typeCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getRoom().getRoomType()));

        TableColumn<Reservation, String> checkInCol = new TableColumn<>("Check-In");
        checkInCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getCheckInDate().toString()));

        TableColumn<Reservation, String> checkOutCol = new TableColumn<>("Check-Out");
        checkOutCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getCheckOutDate().toString()));

        TableColumn<Reservation, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        resTable.getColumns().addAll(idCol, roomCol, typeCol, checkInCol, checkOutCol, statusCol);

        
        resTable.setItems(FXCollections.observableArrayList(customer.getBookingHistory()));

        
        Label cancelLabel = new Label("Cancel a Reservation (by ID):");
        cancelLabel.setStyle("-fx-font-weight: bold;");

        TextField cancelIdField = new TextField();
        cancelIdField.setPromptText("Enter Reservation ID");
        cancelIdField.setPrefWidth(160);

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnAction(e -> {
            String text = cancelIdField.getText().trim();
            if (text.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Missing", "Enter a Reservation ID.");
                return;
            }
            try {
                int id = Integer.parseInt(text);
                MainApp.hotelManager.cancelReservation(id);
                showAlert(Alert.AlertType.INFORMATION, "Cancelled", "Reservation #" + id + " cancelled.");
                resTable.setItems(FXCollections.observableArrayList(customer.getBookingHistory()));
                cancelIdField.clear();
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Invalid", "ID must be a number.");
            } catch (ReservationException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
            }
        });

        Button refreshBtn = new Button("Refresh List");
        refreshBtn.setOnAction(e ->
                resTable.setItems(FXCollections.observableArrayList(customer.getBookingHistory())));

        HBox cancelRow = new HBox(10, new Label("Res. ID:"), cancelIdField, cancelBtn, refreshBtn);
        cancelRow.setAlignment(Pos.CENTER_LEFT);

        layout.getChildren().addAll(headingLabel, resTable, cancelLabel, cancelRow);
        return layout;
    }

  
    private VBox buildServiceTab() {
        VBox layout = new VBox(14);
        layout.setPadding(new Insets(20));

        Label headingLabel = new Label("Request Room Service");
        headingLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

        Label infoLabel = new Label("You must be Checked-In to request services.");
        infoLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: grey;");

        Label resIdLabel = new Label("Reservation ID:");
        TextField resIdField = new TextField();
        resIdField.setPromptText("Enter your Reservation ID");
        resIdField.setPrefWidth(160);

        Label serviceLabel = new Label("Select Service:");
        ComboBox<String> serviceBox = new ComboBox<>();
       
        serviceBox.getItems().addAll(
                RoomService.FOOD,     // "Food Service" - Rs. 500
                RoomService.LAUNDRY,  // "Laundry Service" - Rs. 200
                RoomService.CLEANING  // "Cleaning Service" - Rs. 300
        );
        serviceBox.setValue(RoomService.FOOD);

        
        Label costLabel = new Label("Food: Rs.500  |  Laundry: Rs.200  |  Cleaning: Rs.300");
        costLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #555;");

        Button requestBtn = new Button("Request Service");
        requestBtn.setPrefWidth(160);

        requestBtn.setOnAction(e -> {
            String resText = resIdField.getText().trim();
            String service = serviceBox.getValue();

            if (resText.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Missing", "Please enter your Reservation ID.");
                return;
            }

            try {
                int resId = Integer.parseInt(resText);
               
                MainApp.hotelManager.requestService(resId, service);
                showAlert(Alert.AlertType.INFORMATION, "Service Requested",
                        service + " has been requested for Reservation #" + resId + ".\n"
                        + "Cost: Rs. " + RoomService.getDefaultCost(service));
                resIdField.clear();
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Invalid", "Reservation ID must be a number.");
            } catch (ReservationException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
            }
        });

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(12);
        form.add(resIdLabel, 0, 0);
        form.add(resIdField, 1, 0);
        form.add(serviceLabel, 0, 1);
        form.add(serviceBox, 1, 1);

        layout.getChildren().addAll(headingLabel, infoLabel, form, costLabel, requestBtn);
        return layout;
    }

    
    private VBox buildSearchTab() {
        VBox layout = new VBox(12);
        layout.setPadding(new Insets(15));

        Label headingLabel = new Label("Search Available Rooms");
        headingLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

        Label filterLabel = new Label("Filter by Type:");
        ComboBox<String> typeBox = new ComboBox<>();
        typeBox.getItems().addAll("All", "Standard", "Deluxe", "Suite");
        typeBox.setValue("All");

        Button searchBtn = new Button("Search");

        
        TableView<Room> roomTable = new TableView<>();
        roomTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        roomTable.setPrefHeight(300);

        TableColumn<Room, Integer> numCol = new TableColumn<>("Room No.");
        numCol.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));

        TableColumn<Room, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("roomType"));

        TableColumn<Room, Double> priceCol = new TableColumn<>("Price / Night (Rs.)");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("pricePerNight"));

        TableColumn<Room, Integer> capCol = new TableColumn<>("Capacity");
        capCol.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        roomTable.getColumns().addAll(numCol, typeCol, priceCol, capCol);

        
        roomTable.setItems(FXCollections.observableArrayList(
                MainApp.hotelManager.searchAvailableRooms("All")));

        
        searchBtn.setOnAction(e -> {
            String type = typeBox.getValue();
            roomTable.setItems(FXCollections.observableArrayList(
                    MainApp.hotelManager.searchAvailableRooms(type)));
        });

        HBox searchRow = new HBox(10, filterLabel, typeBox, searchBtn);
        searchRow.setAlignment(Pos.CENTER_LEFT);

        Label priceInfo = new Label("Prices: Standard = Rs.5000/night  |  Deluxe = Rs.10000/night  |  Suite = Rs.15000/night");
        priceInfo.setStyle("-fx-font-size: 12px; -fx-text-fill: #555;");

        layout.getChildren().addAll(headingLabel, searchRow, priceInfo, roomTable);
        return layout;
    }

    
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
