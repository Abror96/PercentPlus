package com.procentplus.retrofit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchRequest {

    @SerializedName("search_params")
    @Expose
    private List<SearchParam> searchParams = null;

    public SearchRequest(List<SearchParam> searchParams) {
        this.searchParams = searchParams;
    }

    public List<SearchParam> getSearchParams() {
        return searchParams;
    }

    public static class SearchParam {

        @SerializedName("param")
        @Expose
        private String param;
        @SerializedName("value")
        @Expose
        private String value;

        public SearchParam(String param, String value) {
            this.param = param;
            this.value = value;
        }

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }

}
