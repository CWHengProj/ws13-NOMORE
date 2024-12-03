package com.ws13Fresh.ws13.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class Person implements Serializable{
    private static final long serialVersionUID =1L;
    //length between 3 and 6x4
    @NotEmpty(message = "This field must not be empty.")
    @Size(min = 3, max = 64, message = "Your name must be between 3 and 64 characters in length.")
    private String name;
    //valid email
    @NotEmpty(message = "This field must not be empty.")
    @Email(message="You have to key in a valid email address.")
    private String email;
    //contains at least 7 digits
    @NotEmpty(message = "This field must not be empty.")
    @Pattern( regexp = "(8|9)[0-9]{7}", message = "Phone number must start with 8 or 9 followed by 7 digits")
    private String phoneNumber;


    
    //TODO age validation, cannot be younger than 10 cannot be older than 100
    @NotNull(message = "This field must not be empty.")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Past( message = "Are you from the future?")
    private LocalDate dob;

    public Person(String name, String email, String phoneNumber, LocalDate dob) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
    }
    public Person() {

    }
    public String getName() {
        return name;
    }
    

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return name + "," + email + "," + phoneNumber + "," + dob;
    }
    
    
}
