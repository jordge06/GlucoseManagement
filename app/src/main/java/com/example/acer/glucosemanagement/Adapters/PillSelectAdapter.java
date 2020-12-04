package com.example.acer.glucosemanagement.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.acer.glucosemanagement.Models.PillSelectCategory;
import com.example.acer.glucosemanagement.R;
import com.example.acer.glucosemanagement.RecyclerInterface;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PillSelectAdapter extends RecyclerView.Adapter<PillSelectAdapter.MainViewHolder> {

    private Context context;
    private List<PillSelectCategory> pillSelectCategoryList;
    private PillsAdapterInterface pillsAdapterInterface;

    public PillSelectAdapter(Context context, List<PillSelectCategory> pillSelectCategoryList) {
        this.context = context;
        this.pillSelectCategoryList = pillSelectCategoryList;
        //pillsAdapterInterface = (PillsAdapterInterface) context;
    }

    public interface PillsAdapterInterface {
        void onItemClick();
    }

    public void setOnClick(PillsAdapterInterface pillsAdapterInterface1) {
        pillsAdapterInterface = pillsAdapterInterface1;
    }

    @NonNull
    @Override
    public PillSelectAdapter.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.pills_selection_template, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PillSelectAdapter.MainViewHolder holder, int position) {
        holder.txtPillsName.setText(pillSelectCategoryList.get(position).getPillsName());
        holder.txtPillsAmount.setText(pillSelectCategoryList.get(position).getPillsAmount());
    }

    @Override
    public int getItemCount() {
        return pillSelectCategoryList.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {

        TextView txtPillsName, txtPillsAmount;
        public MainViewHolder(@NonNull View itemView) {
            super(itemView);

            txtPillsAmount = itemView.findViewById(R.id.txtPillsAmount);
            txtPillsName = itemView.findViewById(R.id.txtPillsName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        pillsAdapterInterface.onItemClick();
                    }
                }
            });
        }
    }
}
