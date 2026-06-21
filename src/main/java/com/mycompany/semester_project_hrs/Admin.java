    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.semester_project_hrs;

/**
 *
 * @author afzii-khan
 */

public class Admin extends Person {

    
    private String adminCode;

    public Admin(String name, String email, String password, String adminCode) {
        super(name, email, password);
        this.adminCode = adminCode;
    }

    @Override
    public String getRole() {
        return "Admin";
    }

    public void setAdminCode(String adminCode){
        this.adminCode=adminCode; 
    }
    public String getAdminCode(){
        return adminCode; 
    }

    public String toString() {
        return "Admin: " + super.toString();
    }
}
