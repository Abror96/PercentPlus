package com.procentplus.retrofit.models;

import com.google.gson.annotations.SerializedName;

public class MobileUser {

    @SerializedName("email")
    String email;
    @SerializedName("password")
    String password;
    @SerializedName("name")
    String name;
    @SerializedName("city")
    String city;

    public MobileUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public MobileUser(String email, String password, String name, String city) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }
}
