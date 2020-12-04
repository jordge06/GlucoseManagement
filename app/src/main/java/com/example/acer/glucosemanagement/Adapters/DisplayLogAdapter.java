package com.example.acer.glucosemanagement.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.acer.glucosemanagement.Models.RowCategory;
import com.example.acer.glucosemanagement.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DisplayLogAdapter extends RecyclerView.Adapter {


    private ArrayList<RowCategory> rowCategoryArrayList;
    private Context context;

    public DisplayLogAdapter(ArrayList<RowCategory> rowCategoryArrayList, Context context) {
        this.rowCategoryArrayList = rowCategoryArrayList;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        String type = rowCategoryArrayList.get(position).getType();

        switch (type) {
            case "sugarLevel":
            case "a1c":
            case "weight":
                return 0;
            case "bloodPressure":
                return 1;
            default:
                return 2;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        switch (viewType) {
            case 0:
                view = layoutInflater.inflate(R.layout.log_display_item_template, parent, false);
                return new MainViewHolder(view);
            case 1:
                view = layoutInflater.inflate(R.layout.bp_display_item_template, parent, false);
                return new BPViewHolder(view);
        }
        view = layoutInflater.inflate(R.layout.pills_display_item_template, parent, false);
        return new DisplayLogAdapter.OthersItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String value = rowCategoryArrayList.get(position).getValue();
        String unit = rowCategoryArrayList.get(position).getUnit();
        String type = rowCategoryArrayList.get(position).getType();
        switch (type) {
            case "sugarLevel":
            case "a1c":
            case "weight":
                MainViewHolder mainViewHolder = (MainViewHolder) holder;
                if (value != null && !value.equals("")) {
                    mainViewHolder.txtType.setText(type.toUpperCase());
                    mainViewHolder.txtValue.setText(value);
                    mainViewHolder.txtUnit.setText(unit);
                    if (unit.equals("mmol/L")) {
                        mainViewHolder.img.setBackgroundResource(rowCategoryArrayList.get(position).getImgUrl());
                    } else {
                        setUpImage(mainViewHolder, mainViewHolder.img, position);
                    }
                } else {
                    mainViewHolder.txtType.setVisibility(View.GONE);
                    mainViewHolder.txtValue.setVisibility(View.GONE);
                    mainViewHolder.txtUnit.setVisibility(View.GONE);
                    mainViewHolder.img.setVisibility(View.GONE);
                }
                break;
            case "bloodPressure":
                BPViewHolder bpViewHolder = (BPViewHolder) holder;
                if (value != null && !value.equals("")) {
                    bpViewHolder.txtSystolic.setText(value);
                    bpViewHolder.txtDiastolic.setText(rowCategoryArrayList.get(position).getSubValue());
                    bpViewHolder.txtType.setText(type);
                } else {
                    bpViewHolder.txtSystolic.setVisibility(View.GONE);
                    bpViewHolder.txtDiastolic.setVisibility(View.GONE);
                    bpViewHolder.txtType.setVisibility(View.GONE);
                    bpViewHolder.img.setVisibility(View.GONE);
                }
                break;
            default:
                OthersItemViewHolder othersItemViewHolder = (OthersItemViewHolder) holder;
                if (value != null && !value.equals("")) {
                    othersItemViewHolder.imgHolder.setBackgroundResource(rowCategoryArrayList.get(position).getImgUrl());
                    othersItemViewHolder.txtValue.setText(value);
//                    if (!rowCategoryArrayList.get(position).getSubValue().equals("")) {
//
//                    } else {
//                        othersItemViewHolder.imgHolder.setBackgroundResource(rowCategoryArrayList.get(position).getImgUrl());
//                        othersItemViewHolder.txtValue.setText(value);
//                    }
                } else {
                    othersItemViewHolder.imgHolder.setVisibility(View.GONE);
                    othersItemViewHolder.txtValue.setVisibility(View.GONE);
                }
                break;
        }
    }

    private void setUpImage(MainViewHolder mainViewHolder, ImageView img, int position) {
        mainViewHolder.img.setBackgroundResource(rowCategoryArrayList.get(position).getImgUrl());
        mainViewHolder.img.setPadding(20, 20, 20, 20);
//        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
//                ConstraintLayout.LayoutParams.WRAP_CONTENT);
//        //params.setMargins(15, 15, 15, 15);
//        mainViewHolder.img.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return rowCategoryArrayList.size();
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder {

        TextView txtValue, txtUnit, txtType;
        ImageView img;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);

            txtValue = itemView.findViewById(R.id.txtValue);
            txtUnit = itemView.findViewById(R.id.txtUnit);
            txtType = itemView.findViewById(R.id.txtType);
            img = itemView.findViewById(R.id.img);
        }
    }

    public static class BPViewHolder extends RecyclerView.ViewHolder {

        TextView txtSystolic, txtDiastolic, txtType;
        ImageView img;

        public BPViewHolder(@NonNull View itemView) {
            super(itemView);

            txtSystolic = itemView.findViewById(R.id.txtSystolic);
            txtDiastolic = itemView.findViewById(R.id.txtDiastolic);
            txtType = itemView.findViewById(R.id.txtType);
            img = itemView.findViewById(R.id.img);
        }
    }


    public static class OthersItemViewHolder extends RecyclerView.ViewHolder {

        ImageView imgHolder;
        TextView txtValue;

        public OthersItemViewHolder(@NonNull View itemView) {
            super(itemView);

            imgHolder = itemView.findViewById(R.id.imgHolder);
            txtValue = itemView.findViewById(R.id.txtValue);
        }
    }

}
