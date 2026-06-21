/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.semester_project_hrs;

import java.util.ArrayList;

/**
 *
 * @author afzii_khan
 */

public interface Searchable {
    
    ArrayList<Room> searchAvailableRooms(String type);
    Customer searchCustomer(String email);
    
}
