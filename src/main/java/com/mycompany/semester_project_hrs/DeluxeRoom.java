/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.semester_project_hrs;

/**
 *
 * @author afzii_khan
 */

public class DeluxeRoom extends Room {


    public DeluxeRoom(int roomNumber) {
        super(roomNumber, 3);
    }

    @Override
    public String getRoomType(){
        return "Deluxe"; 
    }

    @Override
    public double getPricePerNight(){ 
        return 10000.0; 
    }
}
