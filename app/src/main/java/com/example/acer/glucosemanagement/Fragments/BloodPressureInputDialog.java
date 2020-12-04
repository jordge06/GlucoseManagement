package com.example.acer.glucosemanagement.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.acer.glucosemanagement.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class BloodPressureInputDialog extends DialogFragment {

    private BloodPressureInterface bloodPressureInterface;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pills_select, container, false);

        NumberPicker amountSelector = view.findViewById(R.id.amountSelector);
        NumberPicker pillsSelector = view.findViewById(R.id.pillsSelector);
        TextView btnNext = view.findViewById(R.id.btnNext);

        pillsSelector.setMinValue(50);
        pillsSelector.setMaxValue(300);

        amountSelector.setMinValue(20);
        amountSelector.setMaxValue(200);

        amountSelector.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                bloodPressureInterface.setDiastolic(String.valueOf(newVal));
            }
        });

        pillsSelector.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                bloodPressureInterface.setSystolic(String.valueOf(newVal));
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bloodPressureInterface.bpNext();
            }
        });

        setToBottom();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow()
                    .setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                            WindowManager.LayoutParams.WRAP_CONTENT);

            Window window = getDialog().getWindow();
            WindowManager.LayoutParams windowParams = window.getAttributes();
            windowParams.dimAmount = 0.0f;
            windowParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(windowParams);
        }

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        bloodPressureInterface = (BloodPressureInputDialog.BloodPressureInterface) getTargetFragment();
    }

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

    public interface BloodPressureInterface {
        void bpNext();

        void setSystolic(String s);

        void setDiastolic(String s);
    }
}
