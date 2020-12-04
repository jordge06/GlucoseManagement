package com.example.acer.glucosemanagement.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.glucosemanagement.Models.ChildRowCategory;
import com.example.acer.glucosemanagement.Models.ColumnCategory;
import com.example.acer.glucosemanagement.R;
import com.example.acer.glucosemanagement.RecyclerInterface;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ColumnAdapter extends RecyclerView.Adapter<ColumnAdapter.MainViewHolder> {

    private Context context;
    private List<ColumnCategory> columnCategories;
    private RecyclerView.RecycledViewPool viewPool= new RecyclerView.RecycledViewPool();
    private RecyclerInterface recyclerInterface;

    public ColumnAdapter(Context context, List<ColumnCategory> columnCategories) {
        this.context = context;
        this.columnCategories = columnCategories;
        recyclerInterface = (RecyclerInterface) context;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ColumnAdapter.MainViewHolder(LayoutInflater.from(context).inflate(R.layout.main_item_template, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ColumnAdapter.MainViewHolder holder, int position) {
        holder.txtDate.setText(columnCategories.get(position).getDate());
        holder.txtBloodSugarAverageValue.setText(columnCategories.get(position).getAverageBS());
        holder.txtSystolicValue.setText(columnCategories.get(position).getAverageSystolic());
        holder.txtDiastolicValue.setText(columnCategories.get(position).getAverageDiastolic());
        holder.txtA1CAverageValue.setText(columnCategories.get(position).getAverageA1C());

        double bsAverage = Double.parseDouble(String.valueOf(holder.txtBloodSugarAverageValue.getText()).trim());

        if (bsAverage >= 14.0) {
            holder.cardView2.setBackgroundColor(holder.cardView2.getResources().getColor(R.color.customRed));
            holder.expandableLayout.setBackgroundColor(holder.expandableLayout.getResources().getColor(R.color.customRed));
            holder.txtBloodSugarAverageValue.setTextColor(holder.txtBloodSugarAverageValue.getResources().getColor(R.color.customRed));
            holder.txtBSUnit.setTextColor(holder.txtBSUnit.getResources().getColor(R.color.customRed));
        } else if (bsAverage >= 10.0 && bsAverage <= 13.9) {
            holder.cardView2.setBackgroundColor(holder.cardView2.getResources().getColor(R.color.customYellow));
            holder.expandableLayout.setBackgroundColor(holder.expandableLayout.getResources().getColor(R.color.customYellow));
            holder.txtBloodSugarAverageValue.setTextColor(holder.txtBloodSugarAverageValue.getResources().getColor(R.color.customYellow));
            holder.txtBSUnit.setTextColor(holder.txtBSUnit.getResources().getColor(R.color.customYellow));
        } else {
            holder.cardView2.setBackgroundColor(holder.cardView2.getResources().getColor(R.color.colorPrimary));
            holder.expandableLayout.setBackgroundColor(holder.expandableLayout.getResources().getColor(R.color.colorPrimary));
        }

        nestRowRecycler(holder.rvMainItem, columnCategories.get(position).getRowCategoryList());
    }

    @Override
    public int getItemCount() {
        return columnCategories.size();
    }


    public class MainViewHolder extends RecyclerView.ViewHolder {

        TextView txtDate, txtBloodSugarAverageValue, txtSystolicValue, txtDiastolicValue, txtA1CAverageValue, txtBSUnit;
        RecyclerView rvMainItem;
        ImageView btnToggle;
        ExpandableLayout expandableLayout;
        CardView cardView2;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);

            txtDate = itemView.findViewById(R.id.txtDate);
            txtBloodSugarAverageValue = itemView.findViewById(R.id.txtBloodSugarAverageValue);
            txtBSUnit = itemView.findViewById(R.id.txtBSUnit);
            txtSystolicValue = itemView.findViewById(R.id.txtSystolicValue);
            txtDiastolicValue = itemView.findViewById(R.id.txtDiastolicValue);
            txtA1CAverageValue = itemView.findViewById(R.id.txtA1CAverageValue);
            rvMainItem = itemView.findViewById(R.id.rvMainItem);
            btnToggle = itemView.findViewById(R.id.btnToggle);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            cardView2 = itemView.findViewById(R.id.cardView2);



            cardView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerInterface.toggleLayout(expandableLayout, btnToggle);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Clicked!", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    private void nestRowRecycler(RecyclerView recyclerView, List<ChildRowCategory> childRowCategoryList) {
        ChildRowAdapter rowAdapter = new ChildRowAdapter(context, childRowCategoryList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        layoutManager.setInitialPrefetchItemCount(childRowCategoryList.size());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(rowAdapter);
        recyclerView.setRecycledViewPool(viewPool);
    }
}
