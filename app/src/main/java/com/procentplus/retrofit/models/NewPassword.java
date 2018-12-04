package com.procentplus.retrofit.models;

import com.google.gson.annotations.SerializedName;

public class NewPassword {

    @SerializedName("mobile_user")
    private NewPasswordReq mobileUser;

    public NewPassword(NewPasswordReq mobileUser) {
        this.mobileUser = mobileUser;
    }

}
