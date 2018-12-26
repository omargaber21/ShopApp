package com.example.omar.pushnpull;

public class Users {
    public Users() {
    }

    private String name,email,phone;

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    private boolean isAdmin;
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Users(String name, String phone, String email,Boolean isAdmin) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.isAdmin=isAdmin;
    }
}
