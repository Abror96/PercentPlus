package com.procentplus.retrofit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObjectsRequest {

    @SerializedName("activity_type_id")
    @Expose
    private Integer activityTypeId;

    public ObjectsRequest(Integer activityTypeId) {
        this.activityTypeId = activityTypeId;
    }

    public Integer getActivityTypeId() {
        return activityTypeId;
    }


}
