package com.example.mohammadreza_assignment.model;

import com.example.mohammadreza_assignment.R;

public class Person {

    private String firstName;
    private String lastName;
    // image
    private int imageResourceId;

    private String phone;
    private String email;
    private String address;

    public Person() { }

    public Person(String firstName, String lastName, String phone, String email, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        // default photo
        this.imageResourceId = R.drawable.no_photo_available;
    }

    public Person(String firstName, String lastName, int imageResourceId, String phone, String email, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageResourceId = imageResourceId;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return String.format("%s %s\n    Tel: %s\n    Email: %s",
                firstName, lastName, phone, email);
    }
}

