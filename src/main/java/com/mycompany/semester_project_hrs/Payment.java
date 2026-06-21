/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.semester_project_hrs;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author afzii_khan
 */

public class Payment implements Payable, Serializable {


    private double roomCharges;
    private double serviceCharges;
    private boolean isPaid;
    private LocalDate paymentDate;

    public Payment(double roomCharges, double serviceCharges) {
        this.roomCharges = roomCharges;
        this.serviceCharges = serviceCharges;
        this.isPaid = false;
    }

    @Override
    public double calculateTotal() {
        return roomCharges + serviceCharges;
    }

    @Override
    public void processPayment() {
        this.isPaid = true;
        this.paymentDate = LocalDate.now();
    }

    public double getRoomCharges(){
        return roomCharges; 
    }
    
    public double getServiceCharges(){
        return serviceCharges; 
    }
    public boolean isPaid(){
        return isPaid; 
    }
    
    public LocalDate getPaymentDate(){
        return paymentDate; 
    }

    @Override
    public String toString() {
        return "Room: $" + roomCharges + " | Services: $" + serviceCharges +
               " | Total: $" + calculateTotal() + " | Paid: " + isPaid;
    }
}
