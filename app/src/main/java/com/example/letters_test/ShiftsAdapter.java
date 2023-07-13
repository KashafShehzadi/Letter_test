package com.example.letters_test;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShiftsAdapter extends RecyclerView.Adapter<ShiftsAdapter.ViewHolder> {
    private List<List<String>> shiftList;

    public ShiftsAdapter(List<List<String>> shiftList) {
        this.shiftList = shiftList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shift_result, parent, false);
        return new ViewHolder(itemView);
    }




    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<String> shift = shiftList.get(position);
        holder.shiftNumberTextView.setText("Shift " + (position + 1));
        String answersText = TextUtils.join("\n", shift);
        holder.answersTextView.setText(answersText);
    }



    @Override
    public int getItemCount() {
        return shiftList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView shiftNumberTextView, answersTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            shiftNumberTextView = itemView.findViewById(R.id.shiftNumberTextView);
            answersTextView = itemView.findViewById(R.id.answersTextView);
        }
    }
}

