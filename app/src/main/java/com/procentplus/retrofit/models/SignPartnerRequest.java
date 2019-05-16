package com.procentplus.retrofit.models;

import com.google.gson.annotations.SerializedName;

public class SignPartnerRequest {

    @SerializedName("partners_user")

    MobileUser partners_user;

    public SignPartnerRequest(MobileUser partners_user) {
        this.partners_user = partners_user;
    }
}
