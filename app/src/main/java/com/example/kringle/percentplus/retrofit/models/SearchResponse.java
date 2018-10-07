package com.example.kringle.percentplus.retrofit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {

    @SerializedName("point_of_sales")
    @Expose
    private List<PointOfSale> pointOfSales = null;

    public List<PointOfSale> getPointOfSales() {
        return pointOfSales;
    }

    public void setPointOfSales(List<PointOfSale> pointOfSales) {
        this.pointOfSales = pointOfSales;
    }

    public class PointOfSale {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("partner_id")
        @Expose
        private Integer partnerId;
        @SerializedName("city")
        @Expose
        private String city;

        public String getName() {
            return name;
        }

        public Integer getId() {
            return id;
        }

        public Integer getPartnerId() {
            return partnerId;
        }

        public String getCity() {
            return city;
        }

    }

}
