package com.procentplus.retrofit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BonusQRRequest {

    @SerializedName("partner_id")
    @Expose
    private Integer partner_id;

    @SerializedName("point_of_sale_id")
    @Expose
    private Integer point_of_sale_id;

    public BonusQRRequest(Integer partner_id, Integer point_of_sale_id) {
        this.partner_id = partner_id;
        this.point_of_sale_id = point_of_sale_id;
    }

    public Integer getPartner_id() {
        return partner_id;
    }

    public Integer getPoint_of_sale_id() {
        return point_of_sale_id;
    }
}
