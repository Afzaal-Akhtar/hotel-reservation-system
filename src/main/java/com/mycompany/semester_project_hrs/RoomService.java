/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.semester_project_hrs;

import java.io.Serializable;

/**
 *
 * @author afzii_khan
 */

public class RoomService implements Serializable {

    public static final String FOOD     = "Food Service";
    public static final String LAUNDRY  = "Laundry Service";
    public static final String CLEANING = "Cleaning Service";

    private String serviceName;
    private double serviceCost;
    private String status;

    public RoomService(String serviceName, double serviceCost) {
        this.serviceName = serviceName;
        this.serviceCost = serviceCost;
        this.status = "Requested";
    }

    public RoomService(String serviceName) {
        this(serviceName, getDefaultCost(serviceName));
    }

    public static double getDefaultCost(String name) {
        switch (name) {
            case FOOD:
                return 500.0;
            case LAUNDRY:  
                return 200.0;
            case CLEANING: 
                return 300.0;
            default:       
                return 500.0;
        }
    }

    public String getServiceName(){
        return serviceName; 
    }
    
    public double getServiceCost(){
        return serviceCost;
    }
    
    public void setStatus(String status){
        this.status = status; 
    }
    public String getStatus(){
        return status; 
    }
    
    public String toString() {
        return serviceName + " - $" + serviceCost + " [" + status + "]";
    }
}
