/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.semester_project_hrs;

import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author afzii_khan
 */

public class FileManager {

    public static final String CUSTOMERS_FILE    = "customers.data";
    public static final String ROOMS_FILE        = "rooms.data";
    public static final String RESERVATIONS_FILE = "reservations.data";
    
    

    public static <T> void saveList(ArrayList<T> list, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(list);
        }
        catch (IOException e) {
            System.out.println("Error saving " + filename + ": " + e.getMessage());
        }
    }
    
    
    public static <T> ArrayList<T> loadList(String filename) {
        File file = new File(filename);
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filename))) {
            return (ArrayList<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading " + filename + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    
    public static void saveCustomers(ArrayList<Customer> list) {
        saveList(list, CUSTOMERS_FILE);
    }
    public static ArrayList<Customer> loadCustomers() {
        return loadList(CUSTOMERS_FILE);
    }

    public static void saveRooms(ArrayList<Room> list) {
        saveList(list, ROOMS_FILE);
    }
    public static ArrayList<Room> loadRooms() {
        return loadList(ROOMS_FILE);
    }

    public static void saveReservations(ArrayList<Reservation> list) {
        saveList(list, RESERVATIONS_FILE);
    }
    public static ArrayList<Reservation> loadReservations() {
        return loadList(RESERVATIONS_FILE);
    }
}
