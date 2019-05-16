package com.procentplus.retrofit.models;

import com.google.gson.annotations.SerializedName;

public class SignRequest {

    @SerializedName("mobile_user")
    MobileUser mobile_user;

    public SignRequest(MobileUser mobile_user) {
        this.mobile_user = mobile_user;
    }
}
