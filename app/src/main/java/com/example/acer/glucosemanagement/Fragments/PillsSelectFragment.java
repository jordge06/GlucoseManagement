package com.example.acer.glucosemanagement.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import com.example.acer.glucosemanagement.Adapters.PillSelectAdapter;
import com.example.acer.glucosemanagement.Databases.DBUserPills;
import com.example.acer.glucosemanagement.IMainInterface;
import com.example.acer.glucosemanagement.Models.PillSelectCategory;
import com.example.acer.glucosemanagement.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PillsSelectFragment extends Fragment implements View.OnClickListener {

    //widgets
    private RecyclerView rvPills;
    private NumberPicker amountSelector;
    private NumberPicker pillsSelector;

    // instance
    PillSelectAdapter pillSelectAdapter;

    //vars
    private ArrayList<PillSelectCategory> pillSelectCategories = new ArrayList<>();
    private ArrayList<String> pills;
    private ArrayList<String> entriesKey;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_select_pills, container, false);

        // widgets
        Button btnDone = view.findViewById(R.id.btnDone);
        Button btnNew = view.findViewById(R.id.btnNew);
        pillsSelector = view.findViewById(R.id.pillsSelector);
        amountSelector = view.findViewById(R.id.amountSelector);
        rvPills = view.findViewById(R.id.rvPills);

        rvPills.hasFixedSize();

        if (this.getArguments() != null) {

            ArrayList<String> pillsNames = this.getArguments().getStringArrayList(getString(R.string.KEY_PILLS_NAME));
            ArrayList<String> pillsAmounts = this.getArguments().getStringArrayList(getString(R.string.KEY_PILLS_AMOUNT));
            entriesKey = this.getArguments().getStringArrayList(getString(R.string.KEY_ENTRY_KEY));
            if (pillsNames != null && pillsAmounts != null) {
                setDataRecycler(pillsNames, pillsAmounts);
            }
            if (entriesKey != null) {
                //Toast.makeText(getContext(), "" + entriesKey, Toast.LENGTH_SHORT).show();
                pillSelectCategories.add(new PillSelectCategory("-", "-"));
            }
        }

        DBUserPills dbUserPills = new DBUserPills(getContext());
        pills = dbUserPills.getAllPillsData();

        npData();

        setRecyclerValues(pillSelectCategories);

        amountSelector.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int i = pillSelectCategories.size() - 1;
                pillSelectCategories.get(i).setPillsAmount(amountData().get(newVal));
                pillSelectAdapter.notifyItemChanged(i);
            }
        });

        pillsSelector.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int i = pillSelectCategories.size() - 1;
                pillSelectCategories.get(i).setPillsName(pills.get(newVal));
                pillSelectAdapter.notifyItemChanged(i);
            }
        });
        btnNew.setOnClickListener(this);
        btnDone.setOnClickListener(this);

        return view;
    }

    private void setDataRecycler(ArrayList<String> pillsNames, ArrayList<String> pillsAmounts) {
        for (int i = 0; i < pillsNames.size(); i++) {
            pillSelectCategories.add(new PillSelectCategory(pillsNames.get(i), pillsAmounts.get(i)));
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnNew) {
            if (isNotEmpty()) {
                pillSelectCategories.add(new PillSelectCategory("-", "-"));
                pillSelectAdapter.notifyItemInserted(pillSelectCategories.size() - 1);
                amountSelector.setValue(0);
                pillsSelector.setValue(0);
            }

        } else if (v.getId() == R.id.btnDone) {
            //Toast.makeText(getContext(), "" + entriesKey, Toast.LENGTH_SHORT).show();
            //if (isNotEmpty()) iMainInterface.passCategory(pillSelectCategories, entriesKey);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }


    private void setRecyclerValues(List<PillSelectCategory> pillSelectCategoryList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvPills.setLayoutManager(layoutManager);
        pillSelectAdapter = new PillSelectAdapter(getContext(), pillSelectCategoryList);
        rvPills.setAdapter(pillSelectAdapter);
    }

    private Boolean isNotEmpty() {
        int i = pillSelectCategories.size() - 1;

        return (!(pillSelectCategories.get(i).getPillsName().matches("-") ||
                pillSelectCategories.get(i).getPillsAmount().matches("-")));
    }

    public void setNPValues(int arg1, int arg2) {

        amountSelector.setValue(arg2);
        pillsSelector.setValue(arg1);
    }

    private String[] displayData(ArrayList<String> strings) {
        String[] str = new String[strings.size()] ;
        for (int i = 0; i < strings.size(); i++) {
            str[i] = strings.get(i);
        }
        return str;
    }

    private ArrayList<String> amountData() {
        ArrayList<String> data = new ArrayList<>();
        for (int i = 1; i < 41; i++) {
            double d = (i * 0.5);
            data.add(String.valueOf(d));
        }
        data.add(0, "-");

        return data;
    }

    private void npData() {

        int value1 = amountData().size();

        amountSelector.setMinValue(0);
        amountSelector.setMaxValue(value1 - 1);
        amountSelector.setDisplayedValues(displayData(amountData()));
        amountSelector.setWrapSelectorWheel(true);

        pills.add(0, "-");

        int value = pills.size();

        pillsSelector.setMinValue(0);
        pillsSelector.setMaxValue(value - 1);
        pillsSelector.setDisplayedValues(displayData(pills));
        pillsSelector.setWrapSelectorWheel(true);
    }



}
