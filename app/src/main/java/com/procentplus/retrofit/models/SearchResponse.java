package com.procentplus.retrofit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SearchResponse implements Serializable {

    @SerializedName("point_of_sales")
    @Expose
    private List<PointOfSale> pointOfSales = null;

    public SearchResponse() {
        this.pointOfSales = pointOfSales;
    }

    public SearchResponse(List<PointOfSale> pointOfSales) {
        this.pointOfSales = pointOfSales;
    }

    public List<PointOfSale> getPointOfSales() {
        return pointOfSales;
    }

    public void setPointOfSales(List<PointOfSale> pointOfSales) {
        this.pointOfSales = pointOfSales;
    }

}
