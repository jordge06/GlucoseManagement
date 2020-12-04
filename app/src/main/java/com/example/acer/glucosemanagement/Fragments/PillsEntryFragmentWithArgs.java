package com.example.acer.glucosemanagement.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.acer.glucosemanagement.Adapters.PillsAdapter;
import com.example.acer.glucosemanagement.Databases.DBUserPills;
import com.example.acer.glucosemanagement.IMainInterface;
import com.example.acer.glucosemanagement.Models.PillsCategory;
import com.example.acer.glucosemanagement.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PillsEntryFragmentWithArgs extends Fragment implements View.OnClickListener {

    private IMainInterface iMainInterface;
    private RecyclerView rvPills;

    private DBUserPills dbUserPills;

    private List<PillsCategory> pillsCategoryList = new ArrayList<>();

    PillsAdapter pillsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pills_2, container, false);

        Button btnAddPillEntry = v.findViewById(R.id.btnAddPillEntry);
        Button btnSavePillEntry = v.findViewById(R.id.btnSavePillEntry);
        rvPills = v.findViewById(R.id.rvPills);

        btnAddPillEntry.setOnClickListener(this);
        btnSavePillEntry.setOnClickListener(this);

        setRecyclerValues(pillsCategoryList);

        ArrayList<HashMap<String, String>> data = dbUserPills.getAllPills();
        if (!data.isEmpty()) {
            for (Map<String, String> entry : data) {
                inputData(entry.get("userId"), entry.get("userPills"));
            }
        } else {
            pillsCategoryList.add(new PillsCategory(0, ""));

        }

        return v;
    }

    private void inputData(String userId, String userPills) {
        pillsCategoryList.add(new PillsCategory(Integer.parseInt(userId), userPills));
    }

    private void setRecyclerValues(List<PillsCategory> pillsCategories) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvPills.setLayoutManager(layoutManager);
        pillsAdapter = new PillsAdapter(pillsCategories, getContext());
        rvPills.setAdapter(pillsAdapter);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        iMainInterface = (IMainInterface) getActivity();
        dbUserPills = new DBUserPills(context);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAddPillEntry) {
            if (isNotEmpty()) {
                pillsCategoryList.add(new PillsCategory(pillsCategoryList.size() - 1, ""));
                pillsAdapter.notifyItemInserted(pillsCategoryList.size() - 1);
            }

        } else if (v.getId() == R.id.btnSavePillEntry) {
            if (isNotEmpty()) {
                iMainInterface.savePillEntries(pillsCategoryList);
            }


        }
    }

    private Boolean isNotEmpty() {
        int index = pillsCategoryList.size() - 1;
        return (!pillsCategoryList.get(index).getPillsEntry().equals(""));
    }
}
