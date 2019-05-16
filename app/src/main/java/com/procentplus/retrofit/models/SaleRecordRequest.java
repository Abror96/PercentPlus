package com.procentplus.retrofit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaleRecordRequest {
    @SerializedName("sale_record")
    @Expose
    private SaleRecord saleRecord;

    public SaleRecordRequest(SaleRecord saleRecord) {
        this.saleRecord = saleRecord;
    }

    public SaleRecord getSaleRecord() {
        return saleRecord;
    }
}
