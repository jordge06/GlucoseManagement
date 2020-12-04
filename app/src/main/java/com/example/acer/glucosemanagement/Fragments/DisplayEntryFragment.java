package com.example.acer.glucosemanagement.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.example.acer.glucosemanagement.Adapters.DisplayLogAdapter;
import com.example.acer.glucosemanagement.IMainInterface;
import com.example.acer.glucosemanagement.Models.RowCategory;
import com.example.acer.glucosemanagement.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DisplayEntryFragment extends Fragment implements View.OnClickListener {

    // vars
    private ArrayList<Integer> idList = new ArrayList<>();
    private ArrayList<RowCategory> rowCategoryArrayList;

    // instance
    private IMainInterface iMainInterface;

    //widget
    RecyclerView rvData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display_item, container, false);

        Button btnDelete = view.findViewById(R.id.btnDelete);
        Button btnEdit = view.findViewById(R.id.btnEdit);
        rvData = view.findViewById(R.id.rvData);
        TextView txtTime = view.findViewById(R.id.txtTime);
        TextView txtDate = view.findViewById(R.id.txtDate);

        if (this.getArguments() != null) {
            rowCategoryArrayList = this.getArguments().getParcelableArrayList("Sample_Key");
            if (rowCategoryArrayList != null) {
                Toast.makeText(getContext(), "" + rowCategoryArrayList.size(), Toast.LENGTH_SHORT).show();
                for (RowCategory rowCategory : rowCategoryArrayList) {
                    idList.add(rowCategory.getItemId());

                    txtTime.setText(rowCategory.getTime());
                    txtDate.setText(rowCategory.getDate());

                }
                setRecyclerValues(rowCategoryArrayList);
            }
        }
        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        return view;
    }

    private void setRecyclerValues(ArrayList<RowCategory> rowCategoryArrayList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvData.setLayoutManager(layoutManager);
        DisplayLogAdapter displayLogAdapter = new DisplayLogAdapter(rowCategoryArrayList, getContext());
        rvData.setAdapter(displayLogAdapter);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        iMainInterface = (IMainInterface) getActivity();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnEdit) {
            iMainInterface.passDataToAddEntry(rowCategoryArrayList);
        } else if (v.getId() == R.id.btnDelete) {
            iMainInterface.deleteEntry(idList.get(0));
        }
    }
}
