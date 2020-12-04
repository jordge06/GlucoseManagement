package com.example.acer.glucosemanagement.Models;

public class LogsDisplayItem {

    String value;
    String unit;
    String type;
    Integer imgUrl;

    public LogsDisplayItem(String value, String unit, String type, Integer imgUrl) {
        this.value = value;
        this.unit = unit;
        this.type = type;
        this.imgUrl = imgUrl;
    }

    public String getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    public String getType() {
        return type;
    }

    public Integer getImgUrl() {
        return imgUrl;
    }
}
