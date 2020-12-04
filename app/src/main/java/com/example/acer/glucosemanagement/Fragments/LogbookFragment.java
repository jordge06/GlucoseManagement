package com.example.acer.glucosemanagement.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.acer.glucosemanagement.Adapters.ColumnAdapter;
import com.example.acer.glucosemanagement.Databases.Log;
import com.example.acer.glucosemanagement.Databases.LogViewModel;
import com.example.acer.glucosemanagement.IMainInterface;
import com.example.acer.glucosemanagement.Models.ChildRowCategory;
import com.example.acer.glucosemanagement.Models.RowCategory;
import com.example.acer.glucosemanagement.Models.ColumnCategory;
import com.example.acer.glucosemanagement.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LogbookFragment extends Fragment implements View.OnClickListener {

    // widgets
    private LineChart mainGraph;
    private RecyclerView rvMain;
    private Button btnAddEntry;

    // vars
    private List<String> userDates = new ArrayList<>();
    private List<String> userA1c = new ArrayList<>();
    private List<String> userDiastolic = new ArrayList<>();
    private List<String> userSystolic = new ArrayList<>();
    private List<String> userBloodSugar = new ArrayList<>();
    private int bloodSugarPrefs;
    private int weightPrefs;
    private int a1cPrefs;

    // instance
    private IMainInterface iMainInterface;
    private DecimalFormat decimalFormat = new DecimalFormat("0.0");
    private LogViewModel logViewModel;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        iMainInterface = (IMainInterface) getActivity();
        logViewModel = new ViewModelProvider(this).get(LogViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logbook, container, false);

        mainGraph = view.findViewById(R.id.mainGraph);
        rvMain = view.findViewById(R.id.rvMain);
        btnAddEntry = view.findViewById(R.id.btnAddEntry);
        Spinner spType = view.findViewById(R.id.spType);

        if (getContext() != null) {
            spType.setAdapter(setSpAdapter(getContext()));
            SharedPreferences sharedPreferences = getContext().getSharedPreferences(getString(R.string.KEY_SHARED_PREFS), Context.MODE_PRIVATE);

            bloodSugarPrefs = sharedPreferences.getInt(getContext().getString(R.string.KEY_BLOOD_SUGAR_SETTINGS), 0);
            weightPrefs = sharedPreferences.getInt(getContext().getString(R.string.KEY_WEIGHT_SETTINGS), 0);
            a1cPrefs = sharedPreferences.getInt(getContext().getString(R.string.KEY_UNIT), 0);
        }

        try {
            userA1c = logViewModel.getA1cLevel();
            userBloodSugar = logViewModel.getBloodSugar();
            userSystolic = logViewModel.getSystolic();
            userDiastolic = logViewModel.getDiastolic();
            userDates = logViewModel.getDateList();
            show();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                lineGraphData(selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAddEntry.setOnClickListener(this);

        return view;
    }

    // Getting all data from Database and setting up those data to the RecyclerView

    private void show() throws ExecutionException, InterruptedException {
        List<ColumnCategory> columnCategories = new ArrayList<>();
        List<List<ChildRowCategory>> childList = new ArrayList<>();

        ArrayList<ArrayList<String>> sugarList = new ArrayList<>();
        ArrayList<ArrayList<String>> systolicBp = new ArrayList<>();
        ArrayList<ArrayList<String>> diastolicBp = new ArrayList<>();
        ArrayList<ArrayList<String>> a1cList = new ArrayList<>();

        String bloodSugarUnit = bloodSugarPrefs == 0 ? "mg/dL" : "mmol/L";
        String a1cUnit = a1cPrefs == 0 ? "%" : "mmol/mol";
        String weightUnit = weightPrefs == 0 ? "Kg" : "lbs";
        String bloodPressureUnit = "mmh/g";

        // Check first if the Recycler Adapter is Empty before inflating it
        // Checking is necessary to avoid duplicate data every time the Fragment is Called/Open
        if (columnCategories.isEmpty()) {

            for (String s : userDates) {

                List<Log> userData = logViewModel.getLogByDate(s);
                List<ChildRowCategory> childRowCategories = new ArrayList<>();
                ArrayList<String> userSugarLevelList = new ArrayList<>();
                ArrayList<String> userSystolicPressure = new ArrayList<>();
                ArrayList<String> userDiastolicPressure = new ArrayList<>();
                ArrayList<String> userA1CLevelList = new ArrayList<>();

                for (Log log : userData) {

                    ArrayList<RowCategory> rowCategories = new ArrayList<>();

                    int id = log.getId();
                    String date = log.getDate();
                    String time = log.getTime();

                    String sugarLevel = log.getGlucoseLevel();
                    String systolicPressure = log.getSystolic();
                    String diastolicPressure = log.getDiastolic();
                    String a1cLevel = log.getA1cLevel();
                    String pills = log.getPills();
                    String amount = log.getPillsAmount();
                    String weight = log.getWeight();
                    String event = log.getEvent();
                    String tag = log.getTags();
                    String notes = log.getNotes();

                    rowCategories.add(new RowCategory(id, R.drawable.ic_normal, date, "sugarLevel", time, sugarLevel, "", bloodSugarUnit));
                    rowCategories.add(new RowCategory(id, R.drawable.ic_bp, date, "bloodPressure", time, systolicPressure, diastolicPressure, bloodPressureUnit));
                    rowCategories.add(new RowCategory(id, R.drawable.ic_a1c, date, "a1c", time, a1cLevel, "", a1cUnit));
                    rowCategories.add(new RowCategory(id, R.drawable.ic_pill, date, "pill", time, pills, amount, ""));
                    rowCategories.add(new RowCategory(id, R.drawable.ic_weight, date, "weight", time, weight, "", weightUnit));
                    rowCategories.add(new RowCategory(id, R.drawable.ic_event, date, "event", time, event, "", ""));
                    rowCategories.add(new RowCategory(id, R.drawable.ic_book, date, "tag", time, tag, "", ""));
                    rowCategories.add(new RowCategory(id, R.drawable.ic_book, date, "note", time, notes, "", ""));

                    userSugarLevelList.add(sugarLevel);
                    userSystolicPressure.add(systolicPressure);
                    userDiastolicPressure.add(diastolicPressure);
                    userA1CLevelList.add(a1cLevel);

                    // Setup the ArrayList that will be pass to the Nested Recyclerview
                    childRowCategories.add(0, new ChildRowCategory(rowCategories, time));

                }

                childList.add(childRowCategories);
                sugarList.add(userSugarLevelList);
                systolicBp.add(userSystolicPressure);
                diastolicBp.add(userDiastolicPressure);
                a1cList.add(userA1CLevelList);
            }

            for (int j = 0; j < childList.size(); j++) {

                columnCategories.add(new ColumnCategory(
                        userDates.get(j),
                        getAverage(systolicBp.get(j)),
                        getAverage(diastolicBp.get(j)),
                        getAverage(sugarList.get(j)),
                        getAverage(a1cList.get(j)),
                        childList.get(j)
                ));

                // Setup the RecyclerView then pass the Final ArrayList
                setRecyclerValues(columnCategories);
            }
        } else { // If its not empty then inflate the current data to the Recycler Adapter
            setRecyclerValues(columnCategories);
        }
    }

    // Setting up the RecyclerView
    private void setRecyclerValues(List<ColumnCategory> columnCategoryList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvMain.setLayoutManager(layoutManager);
        ColumnAdapter columnAdapter = new ColumnAdapter(getContext(), columnCategoryList);
        rvMain.setAdapter(columnAdapter);
        rvMain.hasFixedSize();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAddEntry) {
            iMainInterface.inflateFragment(btnAddEntry.getTag().toString());
        }
    }

    private ArrayAdapter<CharSequence> setSpAdapter(@NonNull Context context) {
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(context, R.array.graphDataType, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return arrayAdapter;
    }

    private String getAverage(ArrayList<String> dataList) {
        double average;
        ArrayList<Double> sumList = new ArrayList<>();
        for (String s : dataList) {
            if (s != null && !s.equals("")) {
                sumList.add(Double.parseDouble(s));
            }
        }
        double sum = getSum(sumList);
        average = sum / sumList.size();
        if (Double.isNaN(average)) return "0.0";

        return decimalFormat.format(average);
    }

    private Double getSum(ArrayList<Double> dataList) {
        double entry = 0.0;
        for (Double d : dataList) {
            entry += d;
        }
        return entry;
    }

    private void lineGraphData(String selection) {
        mainGraph.setDragEnabled(true);
        mainGraph.setScaleEnabled(false);
        mainGraph.setTouchEnabled(true);
        mainGraph.setPinchZoom(true);
        mainGraph.getDescription().setEnabled(false);

        XAxis xAxis = mainGraph.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        mainGraph.getAxisLeft().setDrawGridLines(false);
        mainGraph.getAxisRight().setDrawGridLines(false);

//        ArrayList<HashMap<String, String>> columnData1 = dbUserLogbook.getSugarLevel();
//        ArrayList<HashMap<String, String>> columnData2 = dbUserLogbook.getSystolic();
//        ArrayList<HashMap<String, String>> columnData3 = dbUserLogbook.getDiastolic();
//        ArrayList<HashMap<String, String>> columnData4 = dbUserLogbook.getA1CLevel();

        List<String> columnData1 = userBloodSugar;
        List<String> columnData2 = userSystolic;
        List<String> columnData3 = userDiastolic;
        List<String> columnData4 = userA1c;


        switch (selection) {
            case "Glucose Level":
                plotGraph(columnData1);
                break;
            case "Systolic Pressure":
                plotGraph(columnData2);
                break;
            case "Diastolic Pressure":
                plotGraph(columnData3);
                break;
            case "A1C Level":
                plotGraph(columnData4);
                break;
        }
    }

    private void plotGraph(List<String> columnData) {
        ArrayList<Entry> yValues = new ArrayList<>();
        for (int i = 0; i < columnData.size(); i++) {
            String value = columnData.get(i);
            if (value != null && !value.equals("")) {
                yValues.add(new BarEntry(i, Float.parseFloat(value)));
            }
        }
        showLineGraph(yValues);
    }

    private void showLineGraph(ArrayList<Entry> yValues) {
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        LineDataSet set1;

        if (mainGraph.getData() != null && mainGraph.getData().getDataSetCount() > 0) {
            dataSets.clear();
            set1 = (LineDataSet) mainGraph.getData().getDataSetByIndex(0);
            set1.setValues(yValues);
            mainGraph.getData().notifyDataChanged();
        } else {
            set1 = new LineDataSet(yValues, "Glucose Level");
            graphDesign(set1);
            dataSets.add(set1);
            LineData data = new LineData(dataSets);
            mainGraph.setData(data);
        }

        mainGraph.setVisibleXRangeMaximum(10);
        mainGraph.notifyDataSetChanged();
        mainGraph.invalidate();
    }

    private void graphDesign(LineDataSet dataSet) {

        dataSet.setDrawIcons(false);
        dataSet.enableDashedLine(10f, 5f, 0f);
        dataSet.enableDashedHighlightLine(10f, 5f, 0f);
        dataSet.setColor(Color.rgb(240, 172, 0));
        dataSet.setCircleColor(Color.rgb(142, 185, 39));
        dataSet.setLineWidth(2f);
        dataSet.setCircleRadius(6f);
        dataSet.setDrawCircleHole(true);
        dataSet.setValueTextSize(8f);
        dataSet.setValueTypeface(Typeface.DEFAULT_BOLD);
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(Color.rgb(142, 185, 39));
        dataSet.setFormLineWidth(1f);
        dataSet.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        dataSet.setFormSize(15.f);
        mainGraph.getAxisRight().setEnabled(false);
    }

}


// For SQLite Only

//    private void databaseMethod() {
//        ArrayList<String> userDateList = dbUserLogbook.getDates();
//        List<ColumnCategory> columnCategories = new ArrayList<>();
//        List<List<ChildRowCategory>> childList = new ArrayList<>();
//
//        ArrayList<ArrayList<String>> sugarList = new ArrayList<>();
//        ArrayList<ArrayList<String>> systolicBp = new ArrayList<>();
//        ArrayList<ArrayList<String>> diastolicBp = new ArrayList<>();
//        ArrayList<ArrayList<String>> a1cList = new ArrayList<>();
//
//        // Check first if the Recycler Adapter is Empty before inflating it
//        // Checking is necessary to avoid duplicate data every time the Fragment is Called/Open
//        if (columnCategories.isEmpty()) {
//
//            for (int i = 0; i < userDateList.size(); i++) {
//
//                ArrayList<HashMap<String, String>> userDataByDate = dbUserLogbook.getUserByDate(userDateList.get(i));
//
//                List<ChildRowCategory> childRowCategories = new ArrayList<>();
//                ArrayList<String> userSugarLevelList = new ArrayList<>();
//                ArrayList<String> userSystolicPressure = new ArrayList<>();
//                ArrayList<String> userDiastolicPressure = new ArrayList<>();
//                ArrayList<String> userA1CLevelList = new ArrayList<>();
//
//                //int maxIndex = userDataByDate.size() - 1;
//
//                for (Map<String, String> entry : userDataByDate) {
//
//                    // Setup the ArrayList that will be pass to the Second Nested Recyclerview
//
//                    ArrayList<RowCategory> rowCategories = new ArrayList<>();
//
//                    String id = entry.get(DBUserLogbook.KEY_ID);
//                    String date = entry.get(DBUserLogbook.KEY_DATE);
//                    String time = entry.get(DBUserLogbook.KEY_TIME);
//
//                    if (id != null) {
//                        String sugarLevel = entry.get(DBUserLogbook.KEY_SUGAR_LEVEL);
//                        String systolicPressure = entry.get(DBUserLogbook.KEY_SYSTOLIC_PRESSURE);
//                        String diastolicPressure = entry.get(DBUserLogbook.KEY_DIASTOLIC_PRESSURE);
//                        String a1cLevel = entry.get(DBUserLogbook.KEY_A1C_LEVEL);
//                        String pills = entry.get(DBUserLogbook.KEY_PILLS);
//                        String amount = entry.get(DBUserLogbook.KEY_PILLS_AMOUNT);
//                        String weight = entry.get(DBUserLogbook.KEY_WEIGHT);
//                        String event = entry.get(DBUserLogbook.KEY_EVENT);
//                        String tag = entry.get(DBUserLogbook.KEY_TAGS);
//                        String notes = entry.get(DBUserLogbook.KEY_NOTES);
//
//                        if (isNotEmpty(sugarLevel))
//                            rowCategories.add(new RowCategory(Integer.parseInt(id), R.drawable.ic_normal, date, "sugarLevel", time, sugarLevel, "", bloodSugar));
//                        if (isNotEmpty(systolicPressure))
//                            rowCategories.add(new RowCategory(Integer.parseInt(id), R.drawable.ic_bp, date, "bloodPressure", time, systolicPressure, diastolicPressure, bloodPressure));
//                        if (isNotEmpty(a1cLevel))
//                            rowCategories.add(new RowCategory(Integer.parseInt(id), R.drawable.ic_a1c, date, "a1c", time, a1cLevel, "", a1c));
//                        if (isNotEmpty(pills))
//                            rowCategories.add(new RowCategory(Integer.parseInt(id), R.drawable.ic_pill, date, "pill", time, pills, amount, ""));
//                        if (isNotEmpty(weight))
//                            rowCategories.add(new RowCategory(Integer.parseInt(id), R.drawable.ic_weight, date, "weight", time, weight, "", kg));
//                        if (isNotEmpty(event))
//                            rowCategories.add(new RowCategory(Integer.parseInt(id), R.drawable.ic_event, date, "event", time, event, "", ""));
//                        if (isNotEmpty(tag))
//                            rowCategories.add(new RowCategory(Integer.parseInt(id), R.drawable.ic_book, date, "tag", time, tag, "", ""));
//                        if (isNotEmpty(notes))
//                            rowCategories.add(new RowCategory(Integer.parseInt(id), R.drawable.ic_book, date, "note", time, notes, "", ""));
//                    }
//
//                    userSugarLevelList.add(entry.get(DBUserLogbook.KEY_SUGAR_LEVEL));
//                    userSystolicPressure.add(entry.get(DBUserLogbook.KEY_SYSTOLIC_PRESSURE));
//                    userDiastolicPressure.add(entry.get(DBUserLogbook.KEY_DIASTOLIC_PRESSURE));
//                    userA1CLevelList.add(entry.get(DBUserLogbook.KEY_A1C_LEVEL));
//
//                    // Setup the ArrayList that will be pass to the Nested Recyclerview
//                    childRowCategories.add(0, new ChildRowCategory(rowCategories, entry.get("userTime")));
//
//                }
//
//                childList.add(childRowCategories);
//                sugarList.add(userSugarLevelList);
//                systolicBp.add(userSystolicPressure);
//                diastolicBp.add(userDiastolicPressure);
//                a1cList.add(userA1CLevelList);
//            }
//
//            for (int j = 0; j < childList.size(); j++) {
//
//                columnCategories.add(new ColumnCategory(
//                        userDateList.get(j),
//                        getAverage(systolicBp.get(j)),
//                        getAverage(diastolicBp.get(j)),
//                        getAverage(sugarList.get(j)),
//                        getAverage(a1cList.get(j)),
//                        childList.get(j)
//                ));
//
//                // Setup the RecyclerView then pass the Final ArrayList
//                setRecyclerValues(columnCategories);
//            }
//
//        } else { // If its not empty then inflate the current data to the Recycler Adapter
//            setRecyclerValues(columnCategories);
//        }
//    }