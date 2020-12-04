package com.example.acer.glucosemanagement.Adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.acer.glucosemanagement.Databases.DBUserPills;
import com.example.acer.glucosemanagement.Models.PillsCategory;
import com.example.acer.glucosemanagement.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PillsAdapter extends RecyclerView.Adapter<PillsAdapter.MainViewHolder> {

    private List<PillsCategory> pillsCategories;
    private Context context;
    private DBUserPills dbUserPills;

    public PillsAdapter(List<PillsCategory> pillsCategories, Context context) {
        this.pillsCategories = pillsCategories;
        this.context = context;
        dbUserPills = new DBUserPills(context);
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.pills_entry_template, parent, false);
        return new MainViewHolder(v, new MyCustomEditTextListener());
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.myCustomEditTextListener.updatePosition(holder.getAdapterPosition());
        holder.txtPillsEntry.setText(pillsCategories.get(position).getPillsEntry());
    }

    @Override
    public int getItemCount() {
        return pillsCategories.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {

        private EditText txtPillsEntry;
        private MyCustomEditTextListener myCustomEditTextListener;

        public MainViewHolder(@NonNull View itemView, MyCustomEditTextListener myCustomEditTextListener) {
            super(itemView);

            txtPillsEntry = itemView.findViewById(R.id.txtPillsEntry);
            Button btnDelete = itemView.findViewById(R.id.btnDelete);
            this.myCustomEditTextListener = myCustomEditTextListener;
            txtPillsEntry.addTextChangedListener(myCustomEditTextListener);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    ArrayList<String> id = new ArrayList<>();
//                    ArrayList<HashMap<String, String>> data = dbUserPills.getAllPills();
//                    for (Map<String, String> entry : data) {
//                        String s = entry.get("user");
//                        id.add(s);
//                    }

                    //Toast.makeText(context, "" + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    int position = getAdapterPosition();
                    pillsCategories.remove(position);
                    dbUserPills.deletePill(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, pillsCategories.size());
                }
            });
        }
    }

    private class MyCustomEditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            pillsCategories.get(position).setPillsEntry(s.toString());
            pillsCategories.get(position).setPillsId(position);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
