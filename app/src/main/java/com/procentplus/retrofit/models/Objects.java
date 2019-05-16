package com.procentplus.retrofit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Objects {

    @SerializedName("activity_type")
    @Expose
    private ObjectsData activityType;

    public ObjectsData getActivityType() {
        return activityType;
    }

    public class ObjectsData {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("partners")
        @Expose
        private List<Object> partners = null;

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public List<Object> getPartners() {
            return partners;
        }

    }

    public class Object {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("activity_type_id")
        @Expose
        private Integer activityTypeId;
        @SerializedName("city")
        @Expose
        private String city;

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Integer getActivityTypeId() {
            return activityTypeId;
        }

        public String getCity() {
            return city;
        }

    }

}
