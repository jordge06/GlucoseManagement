package com.example.acer.glucosemanagement.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.example.acer.glucosemanagement.Adapters.PillSelectAdapter;
import com.example.acer.glucosemanagement.Databases.DBUserLogbook;
import com.example.acer.glucosemanagement.Databases.DBUserPills;
import com.example.acer.glucosemanagement.IMainInterface;
import com.example.acer.glucosemanagement.Models.PillSelectCategory;
import com.example.acer.glucosemanagement.Models.RowCategory;
import com.example.acer.glucosemanagement.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AddEntryFragment extends Fragment implements View.OnClickListener, PillsSelectDialog.PillsInterface, PillSelectAdapter.PillsAdapterInterface, BloodPressureInputDialog.BloodPressureInterface {

    //widgets
    private TextView txtTime, txtDate, txtSystolic, txtDiastolic, txtBpUnit;
    private EditText txtSugarLevel, txtWeight, txtNotes, txtTag, txtA1c;
    private Spinner spEvent;
    private LinearLayout pillsEntryHolder;
    private RecyclerView rvPills;
    private Button btnSaveAllEntry;
    private NestedScrollView svMain;

    //instance
    private IMainInterface iMainInterface;
    private DBUserPills dbUserPills;
    private PillSelectAdapter pillSelectAdapter;
    private PillsSelectDialog pillsSelectDialog = new PillsSelectDialog();
    private BloodPressureInputDialog bloodPressureInputDialog = new BloodPressureInputDialog();

    //vars
    public static final String email = "john016dionisio@gmail.com";
    private boolean isUpdating = false;
    private String event1;
    private ArrayList<Integer> idList = new ArrayList<>();
    private ArrayList<String> valueList = new ArrayList<>();
    private ArrayList<String> subValueList = new ArrayList<>();
    private ArrayList<PillSelectCategory> pillSelectCategories = new ArrayList<>();

    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
    private String tempTime = simpleDateFormat.format(calendar.getTime());
    private String tempDate = new SimpleDateFormat("MMM dd, YYYY", Locale.getDefault()).format(calendar.getTime());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_entry, container, false);

        txtTime = view.findViewById(R.id.txtTime);
        txtDate = view.findViewById(R.id.txtDate);


        txtSugarLevel = view.findViewById(R.id.txtSugarLevel);
        txtSystolic = view.findViewById(R.id.txtSystolic);
        txtDiastolic = view.findViewById(R.id.txtDiastolic);
        txtWeight = view.findViewById(R.id.txtWeight);
        txtNotes = view.findViewById(R.id.txtNotes);
        txtTag = view.findViewById(R.id.txtTag);
        txtA1c = view.findViewById(R.id.txtA1c);
        txtBpUnit = view.findViewById(R.id.txtBpUnit);
        pillsEntryHolder = view.findViewById(R.id.pillsEntryHolder);
        LinearLayout llPill = view.findViewById(R.id.llPill);
        LinearLayout bloodPressure = view.findViewById(R.id.bloodPressure);
        spEvent = view.findViewById(R.id.spEvent);
        btnSaveAllEntry = view.findViewById(R.id.btnSaveAllEntry);
        rvPills = view.findViewById(R.id.rvPills);
        svMain = view.findViewById(R.id.svMain);

        setColorsAndText();
        eventAdapter();
        eventItemListener();
        checkArg();

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(getString(R.string.KEY_SHARED_PREFS), Context.MODE_PRIVATE);
        int unitSelection = sharedPreferences.getInt(getString(R.string.KEY_BLOOD_SUGAR_SETTINGS), 0);

        if (unitSelection == 0) {
            txtSugarLevel.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
        } else {
            txtSugarLevel.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
        }

        // Onclick Listeners
        pillsEntryHolder.setOnClickListener(this);
        btnSaveAllEntry.setOnClickListener(this);
        bloodPressure.setOnClickListener(this);
        llPill.setOnClickListener(this);

        setRecyclerValues(pillSelectCategories);
        pillsSelectDialog.setTargetFragment(AddEntryFragment.this, 1);
        bloodPressureInputDialog.setTargetFragment(AddEntryFragment.this, 1);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        iMainInterface = (IMainInterface) getActivity();
        dbUserPills = new DBUserPills(context);
    }

    private void eventItemListener() {
        spEvent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                event1 = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // Adding adapter to Event Spinner
    private void eventAdapter() {
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.event, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEvent.setAdapter(arrayAdapter);
    }

    private void setColorsAndText() {
        txtSugarLevel.setTextColor(Color.rgb(142, 185, 39));
        txtA1c.setTextColor(Color.rgb(142, 185, 39));
        txtWeight.setTextColor(Color.rgb(198, 85, 181));
        txtTag.setTextColor(Color.rgb(49, 51, 53));
        txtNotes.setTextColor(Color.rgb(49, 51, 53));

        txtTime.setText(tempTime);
        txtDate.setText(tempDate);
    }

    // Setting up and Inflating the Pills RecyclerView
    private void setRecyclerValues(List<PillSelectCategory> pillSelectCategoryList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvPills.setLayoutManager(layoutManager);
        pillSelectAdapter = new PillSelectAdapter(getContext(), pillSelectCategoryList);
        rvPills.setAdapter(pillSelectAdapter);
        pillSelectAdapter.setOnClick(AddEntryFragment.this);
    }

    // Check if there is some Arguments that has been pass
    private void checkArg() {
        if (this.getArguments() != null) {
            ArrayList<RowCategory> rowCategories = this.getArguments().getParcelableArrayList("Sample_Key");

            if (rowCategories != null) {
                idList.add(rowCategories.get(0).getItemId());
                String date = rowCategories.get(0).getDate();
                String time = rowCategories.get(0).getTime();
                for (RowCategory row: rowCategories) {
                    valueList.add(row.getValue());
                    subValueList.add(row.getSubValue());
                }

                setData(valueList, subValueList, date, time);
                isUpdating = true;
                btnSaveAllEntry.setText(getString(R.string.btn_saveUpdateEntry));
            }
        } else {
            newPillsRow("-", "-");
        }
    }

    // Getting all the Data to save to Database
    private HashMap<String, String> newEntry() {
        HashMap<String, String> logsList = new HashMap<>();

        String time = txtTime.getText().toString().trim();
        String date = txtDate.getText().toString().trim();
        String sugarLevel = txtSugarLevel.getText().toString().trim();
        String systolic = txtSystolic.getText().toString().trim();
        String diastolic = txtDiastolic.getText().toString().trim();
        String weight = txtWeight.getText().toString().trim();
        String event = event1;
        String tag = txtTag.getText().toString().trim();
        String note = txtNotes.getText().toString().trim();
        String a1c = txtA1c.getText().toString().trim();

        logsList.put(DBUserLogbook.KEY_EMAIL, email);
        logsList.put(DBUserLogbook.KEY_TIME, time);
        logsList.put(DBUserLogbook.KEY_DATE, date);
        logsList.put(DBUserLogbook.KEY_SUGAR_LEVEL, sugarLevel);
        logsList.put(DBUserLogbook.KEY_SYSTOLIC_PRESSURE, systolic);
        logsList.put(DBUserLogbook.KEY_DIASTOLIC_PRESSURE, diastolic);
        logsList.put(DBUserLogbook.KEY_WEIGHT, weight);
        logsList.put(DBUserLogbook.KEY_EVENT, event);
        logsList.put(DBUserLogbook.KEY_TAGS, tag);
        logsList.put(DBUserLogbook.KEY_NOTES, note);
        logsList.put(DBUserLogbook.KEY_A1C_LEVEL, a1c);

        return logsList;
    }

    // Setting data to their destination (Only call when there is arguments available
    private void setData(ArrayList<String> valueList, ArrayList<String> subValueList, String date, String time) {
        txtTime.setText(date);
        txtDate.setText(time);

        txtSugarLevel.setText(valueList.get(0));
        txtSystolic.setText(valueList.get(1));
        txtDiastolic.setText(subValueList.get(1));
        txtA1c.setText(valueList.get(2));
        txtWeight.setText(valueList.get(4));
        setEvent(valueList.get(5));
        txtTag.setText(valueList.get(6));
        txtNotes.setText(valueList.get(7));

        String[] pills = valueList.get(3).split(",");
        ArrayList<String> pillsName = new ArrayList<>(Arrays.asList(pills));
        String[] amounts = subValueList.get(3).split(",");
        ArrayList<String> pillsAmount = new ArrayList<>(Arrays.asList(amounts));

        setPills(pillsName, pillsAmount);
    }

    // Adding row to the Pills RecyclerView (Only call when there is arguments available)
    private void setPills(ArrayList<String> data1, ArrayList<String> data2) {
        for (int i = 0; i < data1.size(); i++) {
            newPillsRow(data1.get(i), data2.get(i));
        }
    }

    private void newPillsRow(String name, String amount) {
        pillSelectCategories.add(new PillSelectCategory(name, amount));
    }

    // Setting up the selected item in Event Spinner (Only call when there is arguments available)
    private void setEvent(String s) {
        switch (s) {
            case "Breakfast":
                spEvent.setSelection(1);
                break;
            case "Lunch":
                spEvent.setSelection(2);
            case "Dinner":
                spEvent.setSelection(3);
                break;
        }
    }


    // All Onclick events
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.pillsEntryHolder || v.getId() == R.id.llPill) {
            if (dbUserPills.getAllPills().isEmpty()) {
                iMainInterface.inflateFragment(pillsEntryHolder.getTag().toString());
            } else {
                pillsSelectDialog.show(getParentFragmentManager(), "MainDialog");
                scrollDown();
            }
        } else if (v.getId() == R.id.btnSaveAllEntry) {
            if (!newEntry().isEmpty()) {
                if (!isUpdating) iMainInterface.addLogs(newEntry(), pillSelectCategories);
                else iMainInterface.updateLogs(pillSelectCategories, newEntry(), idList.get(0));
            } else {
                Toast.makeText(getContext(), "Input At least One", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.bloodPressure) {
            bloodPressureInputDialog.show(getParentFragmentManager(), "BloodPressureInputDialog");
        }
    }

    // PillsInterface Implementation

    // Adding a row in the Pills RecyclerView
    @Override
    public void next(String s1, String s2) {
        if (isNotEmpty()) {
            newPillsRow("-", "-");
            pillSelectAdapter.notifyItemInserted(pillSelectCategories.size() - 1);
            scrollDown();
        } else {
            if (pillsSelectDialog.getDialog() != null) {
                pillsSelectDialog.getDialog().dismiss();
            }
            txtNotes.requestFocus();
        }
    }

    // Setting up the first column of the Pills RecyclerView
    @Override
    public void setPillsName(String s) {
        int index = pillSelectCategories.size() - 1;
        pillSelectCategories.get(index).setPillsName(s);
        pillSelectAdapter.notifyItemChanged(index);
    }

    // Setting up the second column of the Pills RecyclerView
    @Override
    public void setPillsAmount(String s) {
        int index = pillSelectCategories.size() - 1;
        pillSelectCategories.get(index).setPillsAmount(s);
        pillSelectAdapter.notifyItemChanged(index);
    }

    // Checking if the last row in Pills RecyclerView is not empty
    private Boolean isNotEmpty() {
        int i = pillSelectCategories.size() - 1;

        return (!(pillSelectCategories.get(i).getPillsName().matches("-") ||
                pillSelectCategories.get(i).getPillsAmount().matches("-")));
    }

    @Override
    public void onItemClick() {
        if (dbUserPills.getAllPills().isEmpty()) {
            iMainInterface.inflateFragment(pillsEntryHolder.getTag().toString());
        } else {
            pillsSelectDialog.show(getParentFragmentManager(), "MainDialog");
            scrollDown();
        }
    }

    private void scrollDown() {
        svMain.post(new Runnable() {
            @Override
            public void run() {
                svMain.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    @Override
    public void bpNext() {
        txtA1c.requestFocus();
        if (bloodPressureInputDialog.getDialog() != null)
            bloodPressureInputDialog.getDialog().dismiss();

    }

    @Override
    public void setSystolic(String s) {
        txtSystolic.setText(s.trim());
        String display = " " + getString(R.string.blood_pressure_unit);
        txtBpUnit.setText(display);
    }

    @Override
    public void setDiastolic(String s) {
        txtDiastolic.setText(s.trim());
        String display = " " + getString(R.string.blood_pressure_unit);
        txtBpUnit.setText(display);

    }
}
