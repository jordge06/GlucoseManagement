package com.example.acer.glucosemanagement.Models;

import java.util.List;

public class ColumnCategory {

    String date;
    String averageSystolic;
    String averageDiastolic;
    String averageBS;
    String averageA1C;
    List<ChildRowCategory> childRowCategoryList;

    public ColumnCategory(String date, String averageSystolic, String averageDiastolic, String averageBS, String averageA1C, List<ChildRowCategory> childRowCategoryList) {
        this.date = date;
        this.averageBS = averageBS;
        this.averageA1C = averageA1C;
        this.averageSystolic = averageSystolic;
        this.averageDiastolic = averageDiastolic;
        this.childRowCategoryList = childRowCategoryList;
    }

    public String getAverageSystolic() {
        return averageSystolic;
    }

    public String getAverageDiastolic() {
        return averageDiastolic;
    }

    public String getAverageA1C() {
        return averageA1C;
    }

    public String getDate() {
        return date;
    }

    public String getAverageBS() {
        return averageBS;
    }

    public List<ChildRowCategory> getRowCategoryList() {
        return childRowCategoryList;
    }
}
