/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.semester_project_hrs;

/**
 *
 * @author afzii_khan
 */

public class SuiteRoom extends Room {

    public SuiteRoom(int roomNumber) {
        super(roomNumber, 5);
    }

    @Override
    public String getRoomType(){
        return "Suite";
    }

    @Override
    public double getPricePerNight(){
        return 15000.0; 
    }
}