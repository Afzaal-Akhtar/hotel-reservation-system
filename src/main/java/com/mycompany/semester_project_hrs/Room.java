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

public abstract class Room implements Serializable {

    private int roomNumber;
    private boolean isAvailable;
    private int capacity;

    public Room(int roomNumber, int capacity) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.isAvailable = true;
    }

    public abstract String getRoomType();
    public abstract double getPricePerNight();

    public void setRoomNumber(int roomNumber){ 
        this.roomNumber=roomNumber; 
    }
    public int getRoomNumber(){
        return roomNumber;
    }
    
    public void setAvailable(boolean val){
        this.isAvailable = val;
    }
    public boolean isAvailable(){
        return isAvailable; 
    }
    
    public void setCapacity(int capacity){
        this.capacity=capacity; 
    }
    public int getCapacity(){ 
        return capacity; 
    }

    public String toString() {
        return "Room " + roomNumber + " [" + getRoomType() + "] $" +
               getPricePerNight() + "/night | Available: " + isAvailable;
    }
}
