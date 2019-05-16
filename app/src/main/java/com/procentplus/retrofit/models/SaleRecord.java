package com.procentplus.retrofit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaleRecord {
    @SerializedName("id")
    @Expose
    private Integer transaction_id;
    @SerializedName("revenue")
    @Expose
    private Double revenue;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("created_at")
    @Expose
    private String created_at;
    @SerializedName("updated_at")
    @Expose
    private String updated_at;
    @SerializedName("point_of_sale_id")
    @Expose
    private Integer point_of_sale_id;
    @SerializedName("mobile_user_id")
    @Expose
    private Integer mobile_user_id;
    @SerializedName("shift_id")
    @Expose
    private Integer shift_id;
    @SerializedName("partner_id")
    @Expose
    private Integer partner_id;

    public SaleRecord(Integer transaction_id, Double revenue, String date, String created_at, String updated_at, Integer point_of_sale_id, Integer mobile_user_id, Integer shift_id, Integer partner_id) {
        this.transaction_id = transaction_id;
        this.revenue = revenue;
        this.date = date;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.point_of_sale_id = point_of_sale_id;
        this.mobile_user_id = mobile_user_id;
        this.shift_id = shift_id;
        this.partner_id = partner_id;
    }

    public SaleRecord(Double revenue, String date, Integer point_of_sale_id, Integer mobile_user_id, Integer partner_id) {
        this.revenue = revenue;
        this.date = date;
        this.point_of_sale_id = point_of_sale_id;
        this.mobile_user_id = mobile_user_id;
        this.partner_id = partner_id;
    }

    public Integer getTransaction_id() {
        return transaction_id;
    }

    public Double getRevenue() {
        return revenue;
    }

    public String getDate() {
        return date;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public Integer getPoint_of_sale_id() {
        return point_of_sale_id;
    }

    public Integer getMobile_user_id() {
        return mobile_user_id;
    }

    public Integer getShift_id() {
        return shift_id;
    }

    public Integer getPartner_id() {
        return partner_id;
    }
}
