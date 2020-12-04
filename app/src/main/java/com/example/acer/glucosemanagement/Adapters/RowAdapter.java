package com.example.acer.glucosemanagement.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.acer.glucosemanagement.Models.RowCategory;
import com.example.acer.glucosemanagement.R;
import com.example.acer.glucosemanagement.RecyclerInterface;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class RowAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<RowCategory> rowCategoryList;
    private RecyclerInterface recyclerInterface;
    private int unitSelection;

    public RowAdapter(Context context, ArrayList<RowCategory> rowCategoryList) {
        this.context = context;
        this.rowCategoryList = rowCategoryList;

        recyclerInterface = (RecyclerInterface) context;
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.KEY_SHARED_PREFS), Context.MODE_PRIVATE);
        unitSelection = sharedPreferences.getInt(context.getString(R.string.KEY_BLOOD_SUGAR_SETTINGS), 0);
    }

    @Override
    public int getItemViewType(int position) {
        String type = rowCategoryList.get(position).getType();

        switch (type) {
            case "sugarLevel":
            case "a1c":
            case "weight":
                return 0;
            case "bloodPressure":
                return 1;
            case "pill":
            case "event":
                return 2;
        }
        return 3;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view;
        switch (viewType) {
            case 0:
                view = layoutInflater.inflate(R.layout.row_item_template, parent, false);
                return new MainViewHolder(view);
            case 1:
                view = layoutInflater.inflate(R.layout.bp_item_template, parent, false);
                return new BPViewHolder(view);
            case 2:
                view = layoutInflater.inflate(R.layout.pills_item_template, parent, false);
                return new PillViewHolder(view);
            default:
                view = layoutInflater.inflate(R.layout.text_item_template, parent, false);
                return new TextViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        String value = rowCategoryList.get(position).getValue();
        String unit = rowCategoryList.get(position).getUnit();
        String type = rowCategoryList.get(position).getType();

        switch (type) {
            case "sugarLevel":
            case "a1c":
            case "weight":
                MainViewHolder mainViewHolder = (MainViewHolder) holder;
                if (isNotNull(value)) {
                    int bloodSugar = Integer.parseInt(value);
                    mainViewHolder.txtValue.setText(rowCategoryList.get(position).getValue());
                    mainViewHolder.txtUnit.setText(rowCategoryList.get(position).getUnit());
                    setImage(mainViewHolder, bloodSugar, unit, position);
                } else {
                    mainViewHolder.rowTemplate.setLayoutParams(mainViewHolder.params);
                }
                break;
            case "bloodPressure":
                BPViewHolder bpViewHolder = (BPViewHolder) holder;
                if (isNotNull(value)) {
                    bpViewHolder.txtSystolic.setText(value);
                    bpViewHolder.txtDiastolic.setText(rowCategoryList.get(position).getSubValue());
                } else {
                    bpViewHolder.rowTemplate.setLayoutParams(bpViewHolder.params);
                }
                break;
            case "pill":
            case "event":
                PillViewHolder pillViewHolder = (PillViewHolder) holder;
                if (isNotNull(value)) {
                    if (rowCategoryList.get(position).getSubValue().equals("")) {
                        setEventImage(pillViewHolder, value);
                    } else {
                        pillViewHolder.imgPill.setBackgroundResource(R.drawable.ic_pill);
                    }
                }
                else {
                    pillViewHolder.rowTemplate.setLayoutParams(pillViewHolder.params);
                }
                break;
            default:
                TextViewHolder textViewHolder = (TextViewHolder) holder;
                if (isNotNull(value)) textViewHolder.txtText.setText(rowCategoryList.get(position).getValue());
                else textViewHolder.rowTemplate.setLayoutParams(textViewHolder.params);
                break;
        }
    }

    private void setEventImage(PillViewHolder pillViewHolder, String value) {
        switch (value) {
            case "Breakfast":
                pillViewHolder.imgPill.setBackgroundResource(R.drawable.ic_breakfast);
                break;
            case "Lunch":
                pillViewHolder.imgPill.setBackgroundResource(R.drawable.ic_sun);
                break;
            case "Dinner":
                pillViewHolder.imgPill.setBackgroundResource(R.drawable.ic_dinner);
                break;
            default:
                pillViewHolder.imgPill.setBackgroundResource(0);
                break;
        }
    }


    private Boolean isNotNull(String value) {
        return (value != null && !value.equals(""));
    }

    private void setImage(MainViewHolder mainViewHolder, int bloodSugar, String unit, int position) {

        mainViewHolder.img1.setBackgroundResource(R.drawable.ic_normal);
        if (unit.equals("mmol/L") || unit.equals("mg/dL")) {

            if (unitSelection == 0) {
                int newVal = 18 * bloodSugar;
                mainViewHolder.txtValue.setText(String.valueOf(newVal));
                if (bloodSugar > 252) {
                    mainViewHolder.img1.setBackgroundResource(R.drawable.ic_critical_1);
                } else if (bloodSugar >= 180 && bloodSugar <= 234) {
                    mainViewHolder.img1.setBackgroundResource(R.drawable.ic_warning_1);
                }
            } else {
                mainViewHolder.txtValue.setText(rowCategoryList.get(position).getValue());
                if (bloodSugar > 14) {
                    mainViewHolder.img1.setBackgroundResource(R.drawable.ic_critical_1);
                } else if (bloodSugar >= 10 && bloodSugar <= 13) {
                    mainViewHolder.img1.setBackgroundResource(R.drawable.ic_warning_1);
                }
            }
        } else {
            mainViewHolder.img1.setBackgroundResource(rowCategoryList.get(position).getImgUrl());
            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(15, 15, 15, 15);
            mainViewHolder.img1.setLayoutParams(params);
        }
    }

    @Override
    public int getItemCount() {

        return rowCategoryList.size();
    }

    public class BPViewHolder extends RecyclerView.ViewHolder {

        TextView txtDiastolic, txtSystolic;
        ImageView img;
        public ConstraintLayout.LayoutParams params;
        public ConstraintLayout rowTemplate;

        public BPViewHolder(@NonNull View itemView) {
            super(itemView);

            txtDiastolic = itemView.findViewById(R.id.txtDiastolic);
            txtSystolic = itemView.findViewById(R.id.txtSystolic);
            img = itemView.findViewById(R.id.img);
            rowTemplate = itemView.findViewById(R.id.rowTemplate);
            params = new ConstraintLayout.LayoutParams(0, 0);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        passData(rowCategoryList, position);
                    }
                }
            });
        }
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {

        TextView txtValue, txtUnit;
        ImageView img1;
        public ConstraintLayout.LayoutParams params;
        public ConstraintLayout rowTemplate;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            txtValue = itemView.findViewById(R.id.txtValue);
            txtUnit = itemView.findViewById(R.id.txtUnit);
            img1 = itemView.findViewById(R.id.img1);
            rowTemplate = itemView.findViewById(R.id.rowTemplate);
            params = new ConstraintLayout.LayoutParams(0, 0);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        passData(rowCategoryList, position);
                    }
                }
            });
        }
    }

    public class TextViewHolder extends RecyclerView.ViewHolder {

        TextView txtText;
        public ConstraintLayout.LayoutParams params;
        public ConstraintLayout rowTemplate;

        public TextViewHolder(@NonNull View itemView) {
            super(itemView);

            txtText = itemView.findViewById(R.id.txtText);
            rowTemplate = itemView.findViewById(R.id.rowTemplate);
            params = new ConstraintLayout.LayoutParams(0, 0);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        passData(rowCategoryList, position);
                    }
                }
            });
        }
    }

    public class PillViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPill;
        public ConstraintLayout.LayoutParams params;
        public ConstraintLayout rowTemplate;

        public PillViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPill = itemView.findViewById(R.id.imgPill);
            rowTemplate = itemView.findViewById(R.id.rowTemplate);
            params = new ConstraintLayout.LayoutParams(0, 0);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        passData(rowCategoryList, position);
                    }
                }
            });
        }
    }

    private void passData(ArrayList<RowCategory> rowCategoryList, int pos) {
        recyclerInterface.onRowClick(rowCategoryList, pos);
    }

}
