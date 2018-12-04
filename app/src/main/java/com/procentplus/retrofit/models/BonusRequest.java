package com.procentplus.retrofit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BonusRequest {

    @SerializedName("partner_id")
    @Expose
    private Integer partner_id;

    public BonusRequest(Integer activityTypeId) {
        this.partner_id = activityTypeId;
    }

    public Integer getActivityTypeId() {
        return partner_id;
    }

}
