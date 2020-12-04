package com.example.acer.glucosemanagement;

import com.example.acer.glucosemanagement.Models.PillSelectCategory;
import com.example.acer.glucosemanagement.Models.PillsCategory;
import com.example.acer.glucosemanagement.Models.RowCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public interface IMainInterface {

    void inflateFragment(String fragmentTag);

    void savePillEntries(List<PillsCategory> pillsCategoryList);

    void savePillEntries2(List<PillsCategory> pillsCategoryList);

    void addLogs(HashMap<String, String> logsList, ArrayList<PillSelectCategory> pillSelectCategoryArrayList);

    void updateLogs(ArrayList<PillSelectCategory> pillSelectCategory, HashMap<String, String> logsList, int id);

    void passDataToAddEntry(ArrayList<RowCategory> rowCategoryArrayList);

    void deleteEntry(int id);

    void saveSettings(int unitSelection, int weightSelection, int a1cSelection);
}
