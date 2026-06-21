/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.semester_project_hrs;

/**
 *
 * @author afzii_khan
 */

public class StandardRoom extends Room {

    public StandardRoom(int roomNumber) {
        super(roomNumber, 2);
    }

    @Override
    public String getRoomType(){
        return "Standard"; 
    }

    @Override
    public double getPricePerNight(){
        return 5000.0; 
    }
}
