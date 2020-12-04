package com.example.acer.glucosemanagement.Fragments;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MyXAxisValueFormatter extends ValueFormatter {

    private ArrayList<String> dates;

    public MyXAxisValueFormatter(ArrayList<String> dates) {
        this.dates = dates;
    }

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        int position = Math.round(value);

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd", Locale.getDefault());

        return sdf.format(dates.get(position));

        //return sdf.format(position);
        //return dates.get(position);
    }
}


