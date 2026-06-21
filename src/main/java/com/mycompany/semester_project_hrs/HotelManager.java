/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.semester_project_hrs;

import java.time.LocalDate;
import java.util.ArrayList;


class InvalidRoomException extends Exception {
    public InvalidRoomException(String message) {
        super(message);
    }
}


/**
 *
 * @author afzii_khan
 */

public class HotelManager implements Searchable {

    private ArrayList<Customer> customers;
    private ArrayList<Room> rooms;
    private ArrayList<Reservation> reservations;

    
    private static final Admin ADMIN = new Admin("Admin", "admin@hotel.com", "admin123", "ADMIN001");

    public HotelManager() {
        customers    = FileManager.loadCustomers();
        rooms        = FileManager.loadRooms();
        reservations = FileManager.loadReservations();
        
        if (rooms.isEmpty()) seedRooms();
    }

    private void seedRooms() {
        rooms.add(new StandardRoom(101));
        rooms.add(new StandardRoom(102));
        rooms.add(new DeluxeRoom(201));
        rooms.add(new DeluxeRoom(202));
        rooms.add(new SuiteRoom(301));
        FileManager.saveRooms(rooms);
    }


    public boolean adminLogin(String email, String password) {
        return ADMIN.getEmail().equals(email) && ADMIN.getPassword().equals(password);
    }

    public void addRoom(Room room) throws InvalidRoomException {
        for (Room r : rooms) {
            if (r.getRoomNumber() == room.getRoomNumber()) {
                throw new InvalidRoomException("Room " + room.getRoomNumber() + " already exists.");
            }
        }
        rooms.add(room);
        FileManager.saveRooms(rooms);
    }

    public boolean deleteRoom(int roomNumber) throws InvalidRoomException {
        Room found = getRoomByNumber(roomNumber);
        if (found == null) throw new InvalidRoomException("Room not found: " + roomNumber);
        if (!found.isAvailable()) throw new InvalidRoomException("Room is currently booked.");
        rooms.remove(found);
        FileManager.saveRooms(rooms);
        return true;
    }


    public boolean registerCustomer(Customer c) {
        for (Customer existing : customers) {
            if (existing.getEmail().equalsIgnoreCase(c.getEmail())) return false;
        }
        customers.add(c);
        FileManager.saveCustomers(customers);
        return true;
    }

    public Customer customerLogin(String email, String password) {
        for (Customer c : customers) {
            if (c.getEmail().equalsIgnoreCase(email) && c.getPassword().equals(password)) {
                return c;
            }
        }
        return null;
    }

    
    public Reservation bookRoom(Customer customer, int roomNumber,
                                LocalDate checkIn, LocalDate checkOut)
            throws ReservationException, InvalidRoomException {

        Room room = getRoomByNumber(roomNumber);
        if (room == null) throw new InvalidRoomException("Room " + roomNumber + " not found.");
        if (!room.isAvailable()) throw new ReservationException("Room " + roomNumber + " is not available.");
        if (!checkOut.isAfter(checkIn)) throw new ReservationException("Check-out must be after check-in.");

        Reservation res = new Reservation(customer, room, checkIn, checkOut);
        customer.addBooking(res);
        reservations.add(res);
        FileManager.saveReservations(reservations);
        FileManager.saveRooms(rooms);
        return res;
    }

    public void cancelReservation(int reservationId) throws ReservationException {
        Reservation res = getReservationById(reservationId);
        if (res == null) throw new ReservationException("Reservation not found.");
        res.cancel();
        FileManager.saveReservations(reservations);
        FileManager.saveRooms(rooms);
    }

    public void checkIn(int reservationId) throws ReservationException {
        Reservation res = getReservationById(reservationId);
        if (res == null) throw new ReservationException("Reservation not found.");
        res.checkIn();
        FileManager.saveReservations(reservations);
    }

    public void checkOut(int reservationId) throws ReservationException {
        Reservation res = getReservationById(reservationId);
        if (res == null) throw new ReservationException("Reservation not found.");
        res.checkOut();
        FileManager.saveReservations(reservations);
        FileManager.saveRooms(rooms);
    }

    public void requestService(int reservationId, String serviceName) throws ReservationException {
        Reservation res = getReservationById(reservationId);
        if (res == null) throw new ReservationException("Reservation not found.");
        RoomService service = new RoomService(serviceName);
        res.addService(service);
        FileManager.saveReservations(reservations);
    }


    @Override
    public ArrayList<Room> searchAvailableRooms(String type) {
        ArrayList<Room> result = new ArrayList<>();
        for (Room r : rooms) {
            boolean typeMatch = type.equalsIgnoreCase("All") || r.getRoomType().equalsIgnoreCase(type);
            if (r.isAvailable() && typeMatch) result.add(r);
        }
        return result;
    }

    @Override
    public Customer searchCustomer(String email) {
        for (Customer c : customers) {
            if (c.getEmail().equalsIgnoreCase(email)) return c;
        }
        return null;
    }


    public Room getRoomByNumber(int num) {
        for (Room r : rooms) {
            if (r.getRoomNumber() == num) return r;
        }
        return null;
    }

    public Reservation getReservationById(int id) {
        for (Reservation r : reservations) {
            if (r.getReservationId() == id) return r;
        }
        return null;
    }

    public ArrayList<Customer> getCustomers(){
        return customers; 
    }
    
    public ArrayList<Room> getRooms(){
        return rooms; 
    }
    
    public ArrayList<Reservation> getReservations(){
        return reservations; 
    }
    
    public Admin getAdmin(){
        return ADMIN; 
    }
}
