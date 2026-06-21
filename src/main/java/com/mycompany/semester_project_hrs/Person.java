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
//


public abstract class Person implements Serializable {


    private String name;
    private String email;
    private String password;

    public Person(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public abstract String getRole();

    
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return email;
    }

    public void setPassword(String pw){
        this.password = pw;
    }
    public String getPassword(){
        return password;
    }

    public String toString() {
        return "Name: " + name + " | Email: " + email + " | Role: " + getRole();
    }
}
