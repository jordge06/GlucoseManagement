package com.example.acer.glucosemanagement.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.acer.glucosemanagement.Databases.DBUserPills;
import com.example.acer.glucosemanagement.IMainInterface;
import com.example.acer.glucosemanagement.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment implements View.OnClickListener {


    private IMainInterface iMainInterface;
    private DBUserPills dbUserPills;

    private Spinner spUnit, spWeight, spA1C;
    private TextView txtPills;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_settings, container, false);

        spUnit = v.findViewById(R.id.spUnit);
        spWeight = v.findViewById(R.id.spWeight);
        spA1C = v.findViewById(R.id.spA1C);
        Button btnSave = v.findViewById(R.id.btnSaveSettings);
        txtPills = v.findViewById(R.id.txtPills);

        spA1C.setAdapter(setSpAdapter(R.array.a1cUnit));
        spWeight.setAdapter(setSpAdapter(R.array.weightUnit));
        spUnit.setAdapter(setSpAdapter(R.array.bloodSugarUnit));

        setData();

        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<String> pills = dbUserPills.getAllPillsData();

        if (!pills.isEmpty()) {
            for (int i = 0; i < pills.size(); i++) {
                String data = "";
                if (i == pills.size() - 1) {
                    data = pills.get(i);
                } else {
                    data = pills.get(i) + ", ";
                }
                stringBuilder.append(data);
            }
            txtPills.setText(stringBuilder);
        }

        btnSave.setOnClickListener(this);
        txtPills.setOnClickListener(this);

        return v;
    }

    private ArrayAdapter<CharSequence> setSpAdapter(int arrayList) {
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getContext(), arrayList, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return arrayAdapter;
    }

    private void setData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(getString(R.string.KEY_SHARED_PREFS), Context.MODE_PRIVATE);
        int unitSelection = sharedPreferences.getInt(getString(R.string.KEY_BLOOD_SUGAR_SETTINGS), 0);
        int weightSelection = sharedPreferences.getInt(getString(R.string.KEY_WEIGHT_SETTINGS), 0);
        int a1cSelection = sharedPreferences.getInt(getString(R.string.KEY_A1C_SETTINGS), 0);

        spUnit.setSelection(unitSelection);
        spWeight.setSelection(weightSelection);
        spA1C.setSelection(a1cSelection);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dbUserPills = new DBUserPills(context);
        iMainInterface = (IMainInterface) context;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSaveSettings) {
            iMainInterface.saveSettings(spUnit.getSelectedItemPosition(),
                    spWeight.getSelectedItemPosition(),
                    spA1C.getSelectedItemPosition());
        } else if (v.getId() == R.id.txtPills) {
            iMainInterface.inflateFragment(String.valueOf(txtPills.getTag()));
        }

    }
}
