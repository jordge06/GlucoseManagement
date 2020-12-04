package com.example.acer.glucosemanagement;

import android.widget.ImageView;

import com.example.acer.glucosemanagement.Models.PillSelectCategory;
import com.example.acer.glucosemanagement.Models.RowCategory;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

public interface RecyclerInterface {

    void onRowClick(ArrayList<RowCategory> rowCategoryList, int pos);

    void toggleLayout(ExpandableLayout expandableLayout, ImageView imageView);
}
