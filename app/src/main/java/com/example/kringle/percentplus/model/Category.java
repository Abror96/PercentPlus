package com.example.kringle.percentplus.model;

public class Category {

    public String categoryName;
    public int num;

    public Category(String categoryName, int num) {
        this.categoryName = categoryName;
        this.num = num;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getNum() {
        return num;
    }
}
