package com.example.acer.glucosemanagement.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;

import com.example.acer.glucosemanagement.Databases.DBUserPills;
import com.example.acer.glucosemanagement.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class PillsSelectDialog extends DialogFragment implements View.OnClickListener {

    // widgets
    private NumberPicker amountSelector, pillsSelector;

    // instance
    private DBUserPills dbUserPills;
    private PillsInterface pillsInterface;

    private ArrayList<String> pills;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pills_select, container, false);

        TextView btnNext = view.findViewById(R.id.btnNext);
        amountSelector = view.findViewById(R.id.amountSelector);
        pillsSelector = view.findViewById(R.id.pillsSelector);

        pills = dbUserPills.getAllPillsData();

        npData();

        btnNext.setOnClickListener(this);
        setToBottom();

        pillsSelector.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                pillsInterface.setPillsName(displayData(pills)[newVal]);
            }
        });

        amountSelector.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                pillsInterface.setPillsAmount(amountData().get(newVal));
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        pillsInterface.next("-", "-");
        amountSelector.setValue(0);
        pillsSelector.setValue(0);
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
//    }

    @Override
    public void onStart() {
        super.onStart();


        if (getDialog() != null && getDialog().getWindow() != null) {

            // To make the Dialog to match the parent
            Window window = getDialog().getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);

            // To make the background of the dialog not dim
            WindowManager.LayoutParams windowParams = window.getAttributes();
            windowParams.dimAmount = 0.0f;
            windowParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(windowParams);
        }


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Instance of Database and Interface
        dbUserPills = new DBUserPills(getContext());
        pillsInterface = (PillsInterface) getTargetFragment();
    }

    // Cast the dialog to bottom of the screen
    private void setToBottom() {

        if (getDialog() != null && getDialog().getWindow() != null) {
            Window window = getDialog().getWindow();

            window.setGravity(Gravity.BOTTOM);

            WindowManager.LayoutParams params = window.getAttributes();
//        params.x = 300;
//        params.y = 100;

            params.x = 0;
            params.y = 0;
            window.setAttributes(params);
        }

    }

    // Public interface to pass data to the AddEntryFragment

    public interface PillsInterface {
        void next(String s1, String s2);

        void setPillsName(String s);

        void setPillsAmount(String s);
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

    private String[] displayData(ArrayList<String> strings) {
        String[] str = new String[strings.size()];
        for (int i = 0; i < strings.size(); i++) {
            str[i] = strings.get(i);
        }
        return str;
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
