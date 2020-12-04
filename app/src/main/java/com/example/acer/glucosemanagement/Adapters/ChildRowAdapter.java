package com.example.acer.glucosemanagement.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.glucosemanagement.Models.ChildRowCategory;
import com.example.acer.glucosemanagement.Models.RowCategory;
import com.example.acer.glucosemanagement.R;
import com.example.acer.glucosemanagement.RecyclerInterface;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChildRowAdapter extends RecyclerView.Adapter<ChildRowAdapter.MainViewHolder> {

    private Context context;
    private List<ChildRowCategory> childRowCategoryList;

    public ChildRowAdapter(Context context, List<ChildRowCategory> childRowCategoryList) {
        this.context = context;
        this.childRowCategoryList = childRowCategoryList;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.child_row_item_template, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.txtTime.setText(childRowCategoryList.get(position).getTime());
        nestRowRecycler(holder.rvAllRow, childRowCategoryList.get(position).getRowCategoryList());

    }

    @Override
    public int getItemCount() {
        return childRowCategoryList.size();
    }


//    @Override
//    public void onClick(int position) {
//        List<RowCategory> rowCategories = new ArrayList<>();
//        RowCategory clickedChildRowCategory = rowCategories.get(position);
//
//        Toast.makeText(context, "Display Anything: " + rowCategories.get(position), Toast.LENGTH_SHORT).show();
//    }

    public static class MainViewHolder extends RecyclerView.ViewHolder {

        RecyclerView rvAllRow;
        TextView txtTime;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);

            rvAllRow = itemView.findViewById(R.id.rvAllRow);
            txtTime = itemView.findViewById(R.id.txtTime);

        }
    }

    private void nestRowRecycler(RecyclerView recyclerView, ArrayList<RowCategory> rowCategoryList) {
        RowAdapter rowAdapter = new RowAdapter(context, rowCategoryList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        layoutManager.setInitialPrefetchItemCount(rowCategoryList.size());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(rowAdapter);

        //rowAdapter.setOnItemClickListener(this);
    }
}
