package com.example.populant;

import com.google.firebase.database.DatabaseReference;

public class User {

    String userId;
    String name;
    String city;
    String houseNumber;





    public User(){

    }

    public User(String userId, String name, String city, String houseNumber) {
        this.userId = userId;
        this.name = name;
        this.city = city;
        this.houseNumber = houseNumber;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getHouseNumber() {
        return houseNumber;
    }
}
