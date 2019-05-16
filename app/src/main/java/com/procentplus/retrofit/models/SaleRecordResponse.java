package com.procentplus.retrofit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaleRecordResponse {
    @SerializedName("sale_record")
    @Expose
    private SaleRecord sale_record;
    @SerializedName("success")
    @Expose
    private Boolean success;

    public SaleRecordResponse(SaleRecord sale_record, Boolean success) {
        this.sale_record = sale_record;
        this.success = success;
    }

    public SaleRecord getSale_record() {
        return sale_record;
    }

    public Boolean getSuccess() {
        return success;
    }
}
