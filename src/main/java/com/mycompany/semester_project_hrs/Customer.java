/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.semester_project_hrs;

import java.util.ArrayList;
import java.io.Serializable;
/**
 *
 * @author afzii_khan
 */
// 

public class Customer extends Person implements Serializable {


    private int customerId;
    private String phone;
    private ArrayList<Reservation> bookingHistory;   

    private static int counter = 1000;

    public Customer(String name, String email, String password, String phone) {
        super(name, email, password);
        this.customerId = ++counter;
        this.phone = phone;
        this.bookingHistory = new ArrayList<>();
    }

    public Customer(String name, String email, String password) {
        this(name, email, password, "N/A");
    }

    @Override
    public String getRole() {
        return "Customer";
    }

    public void addBooking(Reservation r) {
        bookingHistory.add(r);
    }

    public int getCustomerId(){
        return customerId;
    }
    
    public void setPhone(String phone){
        this.phone = phone;
    }
    public String getPhone(){
        return phone;
    }
    
    public ArrayList<Reservation> getBookingHistory() {
        return bookingHistory;
    }

    public String toString() {
        return "Customer[" + customerId + "] " + super.toString() + " | Phone: " + phone;
    }
}
