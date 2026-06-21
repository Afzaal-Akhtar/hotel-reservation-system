package com.mycompany.semester_project_hrs;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class AdminDashboard {

    private Stage stage;

    public AdminDashboard(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        stage.setTitle("Admin Dashboard - Hotel Reservation System");

        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab roomsTab = new Tab("Manage Rooms", buildRoomsTab());
        Tab reservationsTab = new Tab("All Reservations", buildReservationsTab());
        Tab customersTab = new Tab("All Customers", buildCustomersTab());
        Tab actionsTab = new Tab("Reservation Actions", buildActionsTab());

        tabPane.getTabs().addAll(roomsTab, reservationsTab, customersTab, actionsTab);

        
        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> {
            LoginScreen loginScreen = new LoginScreen(stage);
            loginScreen.show();
        });

        Label welcomeLabel = new Label("Welcome, Admin!");
        welcomeLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        HBox topBar = new HBox(10, welcomeLabel, logoutBtn);
        topBar.setAlignment(Pos.CENTER_RIGHT);
        topBar.setPadding(new Insets(8, 10, 8, 10));
        topBar.setStyle("-fx-background-color: #e8e8e8;");

        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(tabPane);

        Scene scene = new Scene(root, 750, 550);
        stage.setScene(scene);
        stage.setResizable(true);
    }


    private VBox buildRoomsTab() {
        VBox layout = new VBox(12);
        layout.setPadding(new Insets(15));

        Label headingLabel = new Label("Room List");
        headingLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

        // Table to display rooms
        TableView<Room> roomTable = new TableView<>();
        roomTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Room, Integer> numCol = new TableColumn<>("Room No.");
        numCol.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));

        TableColumn<Room, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("roomType"));

        TableColumn<Room, Double> priceCol = new TableColumn<>("Price/Night");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("pricePerNight"));

        TableColumn<Room, Integer> capCol = new TableColumn<>("Capacity");
        capCol.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        TableColumn<Room, Boolean> availCol = new TableColumn<>("Available");
        availCol.setCellValueFactory(new PropertyValueFactory<>("available"));

        roomTable.getColumns().addAll(numCol, typeCol, priceCol, capCol, availCol);
        roomTable.setItems(FXCollections.observableArrayList(MainApp.hotelManager.getRooms()));
        roomTable.setPrefHeight(220);

        
        Label addLabel = new Label("Add New Room:");
        addLabel.setStyle("-fx-font-weight: bold;");

        TextField roomNumField = new TextField();
        roomNumField.setPromptText("Room Number");
        roomNumField.setPrefWidth(130);

        ComboBox<String> roomTypeBox = new ComboBox<>();
        roomTypeBox.getItems().addAll("Standard", "Deluxe", "Suite");
        roomTypeBox.setValue("Standard");

        Button addRoomBtn = new Button("Add Room");
        Button deleteRoomBtn = new Button("Delete Room");

        
        addRoomBtn.setOnAction(e -> {
            String numText = roomNumField.getText().trim();
            if (numText.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Missing Input", "Please enter a room number.");
                return;
            }
            try {
                int roomNum = Integer.parseInt(numText);
                String type = roomTypeBox.getValue();

                
                Room newRoom;
                if (type.equals("Deluxe")) {
                    newRoom = new DeluxeRoom(roomNum);
                } else if (type.equals("Suite")) {
                    newRoom = new SuiteRoom(roomNum);
                } else {
                    newRoom = new StandardRoom(roomNum);
                }

                MainApp.hotelManager.addRoom(newRoom);
                
                roomTable.setItems(FXCollections.observableArrayList(MainApp.hotelManager.getRooms()));
                roomNumField.clear();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Room " + roomNum + " added successfully.");
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Room number must be a number.");
            } catch (InvalidRoomException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
            }
        });

       
        deleteRoomBtn.setOnAction(e -> {
            Room selectedRoom = roomTable.getSelectionModel().getSelectedItem();
            if (selectedRoom == null) {
                showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a room from the table first.");
                return;
            }
            try {
                MainApp.hotelManager.deleteRoom(selectedRoom.getRoomNumber());
                roomTable.setItems(FXCollections.observableArrayList(MainApp.hotelManager.getRooms()));
                showAlert(Alert.AlertType.INFORMATION, "Deleted", "Room deleted successfully.");
            } catch (InvalidRoomException ex) {
                showAlert(Alert.AlertType.ERROR, "Cannot Delete", ex.getMessage());
            }
        });

        HBox addRow = new HBox(10, new Label("Room No:"), roomNumField,
                new Label("Type:"), roomTypeBox, addRoomBtn, deleteRoomBtn);
        addRow.setAlignment(Pos.CENTER_LEFT);

        layout.getChildren().addAll(headingLabel, roomTable, addLabel, addRow);
        return layout;
    }

    private VBox buildReservationsTab() {
        VBox layout = new VBox(12);
        layout.setPadding(new Insets(15));

        Label headingLabel = new Label("All Reservations");
        headingLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

        TableView<Reservation> resTable = new TableView<>();
        resTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        resTable.setPrefHeight(350);

        TableColumn<Reservation, Integer> idCol = new TableColumn<>("Res. ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("reservationId"));

        // Custom column to show customer name
        TableColumn<Reservation, String> custCol = new TableColumn<>("Customer");
        custCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getCustomer().getName()));

        TableColumn<Reservation, String> roomCol = new TableColumn<>("Room");
        roomCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        String.valueOf(data.getValue().getRoom().getRoomNumber())));

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

        resTable.getColumns().addAll(idCol, custCol, roomCol, checkInCol, checkOutCol, statusCol);
        resTable.setItems(FXCollections.observableArrayList(MainApp.hotelManager.getReservations()));

        // Refresh button to reload data
        Button refreshBtn = new Button("Refresh");
        refreshBtn.setOnAction(e ->
                resTable.setItems(FXCollections.observableArrayList(MainApp.hotelManager.getReservations())));

        layout.getChildren().addAll(headingLabel, resTable, refreshBtn);
        return layout;
    }

   
    private VBox buildCustomersTab() {
        VBox layout = new VBox(12);
        layout.setPadding(new Insets(15));

        Label headingLabel = new Label("All Registered Customers");
        headingLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

        TableView<Customer> custTable = new TableView<>();
        custTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        custTable.setPrefHeight(350);

        TableColumn<Customer, Integer> idCol = new TableColumn<>("Customer ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        TableColumn<Customer, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Customer, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Customer, String> phoneCol = new TableColumn<>("Phone");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        custTable.getColumns().addAll(idCol, nameCol, emailCol, phoneCol);
        custTable.setItems(FXCollections.observableArrayList(MainApp.hotelManager.getCustomers()));

        Button refreshBtn = new Button("Refresh");
        refreshBtn.setOnAction(e ->
                custTable.setItems(FXCollections.observableArrayList(MainApp.hotelManager.getCustomers())));

        layout.getChildren().addAll(headingLabel, custTable, refreshBtn);
        return layout;
    }

  
    private VBox buildActionsTab() {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));

        Label headingLabel = new Label("Reservation Actions");
        headingLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

        Label infoLabel = new Label("Enter a Reservation ID and choose an action:");

        
        Label resIdLabel = new Label("Reservation ID:");
        TextField resIdField = new TextField();
        resIdField.setPromptText("e.g. 1001");
        resIdField.setPrefWidth(130);

      
        Button checkInBtn = new Button("Check-In");
        Button checkOutBtn = new Button("Check-Out");
        Button cancelBtn = new Button("Cancel Reservation");

        checkInBtn.setPrefWidth(140);
        checkOutBtn.setPrefWidth(140);
        cancelBtn.setPrefWidth(140);

      
        checkInBtn.setOnAction(e -> {
            int id = getResId(resIdField);
            if (id == -1) return;
            try {
                MainApp.hotelManager.checkIn(id);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Check-In completed for Reservation #" + id);
            } catch (ReservationException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
            }
        });

      
        checkOutBtn.setOnAction(e -> {
            int id = getResId(resIdField);
            if (id == -1) return;
            try {
                MainApp.hotelManager.checkOut(id);
                showBill(id);
            } catch (ReservationException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
            }
        });

        
        cancelBtn.setOnAction(e -> {
            int id = getResId(resIdField);
            if (id == -1) return;
            try {
                MainApp.hotelManager.cancelReservation(id);
                showAlert(Alert.AlertType.INFORMATION, "Cancelled", "Reservation #" + id + " has been cancelled.");
            } catch (ReservationException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
            }
        });

        HBox idRow = new HBox(10, resIdLabel, resIdField);
        idRow.setAlignment(Pos.CENTER_LEFT);

        HBox btnRow = new HBox(10, checkInBtn, checkOutBtn, cancelBtn);
        btnRow.setAlignment(Pos.CENTER_LEFT);

        layout.getChildren().addAll(headingLabel, infoLabel, idRow, btnRow);
        return layout;
    }

   
    private int getResId(TextField field) {
        String text = field.getText().trim();
        if (text.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Missing Input", "Please enter a Reservation ID.");
            return -1;
        }
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Reservation ID must be a number.");
            return -1;
        }
    }

   
    private void showBill(int reservationId) {
        Reservation res = MainApp.hotelManager.getReservationById(reservationId);
        if (res == null || res.getPayment() == null) {
            showAlert(Alert.AlertType.INFORMATION, "Checked Out", "Check-out done. No bill details available.");
            return;
        }

        Payment payment = res.getPayment();
        String billInfo = "=== BILL SUMMARY ===\n"
                + "Customer: " + res.getCustomer().getName() + "\n"
                + "Room: " + res.getRoom().getRoomNumber() + " (" + res.getRoom().getRoomType() + ")\n"
                + "Check-In: " + res.getCheckInDate() + "\n"
                + "Check-Out: " + res.getCheckOutDate() + "\n"
                + "Room Charges: Rs. " + payment.getRoomCharges() + "\n"
                + "Service Charges: Rs. " + payment.getServiceCharges() + "\n"
                + "-------------------\n"
                + "TOTAL: Rs. " + payment.calculateTotal();

        showAlert(Alert.AlertType.INFORMATION, "Bill Generated", billInfo);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
