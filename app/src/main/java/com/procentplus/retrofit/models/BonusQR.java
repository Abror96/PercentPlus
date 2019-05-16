package com.procentplus.retrofit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BonusQR {

    @SerializedName("point_of_sale_id")
    @Expose
    private Integer point_of_sale_id;

    @SerializedName("qrcode")
    @Expose
    private String qrcode;

    @SerializedName("user")
    @Expose
    private UserData user;

    @SerializedName("bonus")
    @Expose
    private BonusData bonus;




    public class UserData {
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("city")
        @Expose
        private String city;

        public Integer getId() { return id; }

        public String getName() { return name; }

        public String getCity() { return city; }
    }


    public class BonusData {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("sum_from")
        @Expose
        private Integer sumFrom;
        @SerializedName("sum_to")
        @Expose
        private Integer sumTo;
        @SerializedName("percent")
        @Expose
        private String percent;

        public Integer getId() {
            return id;
        }

        public Integer getSumFrom() {
            return sumFrom;
        }

        public Integer getSumTo() {
            return sumTo;
        }

        public String getPercent() {
            return percent;
        }

    }

    public Integer getPoint_of_sale_id() {
        return point_of_sale_id;
    }

    public String getQrcode() {
        return qrcode;
    }

    public UserData getUser() {
        return user;
    }

    public BonusData getBonus() {
        return bonus;
    }
}
