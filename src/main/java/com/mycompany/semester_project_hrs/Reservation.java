    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.semester_project_hrs;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

class ReservationException extends Exception {
    public ReservationException(String message) {
        super(message);
        System.out.println(message);
    }
}

/**
 *
 * @author afzii_khan
 */

public class Reservation implements Serializable {

    private static int counter = 1000;

    private int reservationId;
    private Customer customer;
    private Room room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private ArrayList<RoomService> services;
    private Payment payment;
    private String status;

    public Reservation(Customer customer, Room room,
                       LocalDate checkIn, LocalDate checkOut) {
        this.reservationId = ++counter;
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkIn;
        this.checkOutDate = checkOut;
        this.services = new ArrayList<>();
        this.status = "Booked";
        room.setAvailable(false);
    }

    public void addService(RoomService service) {
        services.add(service);
    }

    public void checkIn() {
        if (status.equals("Booked")) {
            status = "CheckedIn";
        }
    }

    public void checkOut() {
        if (status.equals("CheckedIn")) {
            status = "CheckedOut";
            room.setAvailable(true);
            generateBill();
        }
    }

    public void cancel() throws ReservationException {
        if (status.equals("CheckedIn")) {
            throw new ReservationException("Cannot cancel a reservation that is already checked in.");
        }
        status = "Cancelled";
        room.setAvailable(true);
    }

public void generateBill() {
    
    long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    
    double roomCost = nights * room.getPricePerNight();
    
    double serviceCost = 0;
    for (RoomService s : services) {
        serviceCost = serviceCost + s.getServiceCost();
    }
        
    this.payment = new Payment(roomCost, serviceCost);
}


    public void setStatus(String status){
        this.status = status; 
    }
    public String getStatus(){
        return status;
    }
    
    public int getReservationId(){
        return reservationId; 
    }
    
    public Customer getCustomer(){
        return customer; 
    }
    
    public Room getRoom(){
        return room; 
    }
    
    public LocalDate getCheckInDate(){
        return checkInDate; 
    }
    
    public LocalDate getCheckOutDate(){
        return checkOutDate; 
    }
    
    public ArrayList<RoomService> getServices(){
        return services; 
    }
    
    public Payment getPayment(){
        return payment; 
    }
    

    @Override
    public String toString() {
        return "Res#" + reservationId + " | " + customer.getName() +
               " | Room " + room.getRoomNumber() + " | " + checkInDate +
               " to " + checkOutDate + " | " + status;
    }
}
