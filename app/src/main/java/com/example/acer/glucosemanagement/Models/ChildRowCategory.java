package com.example.acer.glucosemanagement.Models;

import java.util.ArrayList;
import java.util.List;

public class ChildRowCategory {

    ArrayList<RowCategory> rowCategoryList;
    String time;

    public ChildRowCategory(ArrayList<RowCategory> rowCategoryList, String time) {
        this.rowCategoryList = rowCategoryList;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public ArrayList<RowCategory> getRowCategoryList() {
        return rowCategoryList;
    }
}
