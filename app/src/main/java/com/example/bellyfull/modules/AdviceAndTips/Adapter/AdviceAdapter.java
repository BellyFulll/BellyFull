package com.example.bellyfull.modules.AdviceAndTips.Adapter;// AdviceAdapter.java

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bellyfull.R;
import com.example.bellyfull.modules.AdviceAndTips.Models.AdviceItem;

import java.util.List;

public class AdviceAdapter extends RecyclerView.Adapter<AdviceAdapter.AdviceViewHolder> {
    private List<AdviceItem> adviceList;

    public AdviceAdapter(List<AdviceItem> adviceList) {
        this.adviceList = adviceList;
    }

    @NonNull
    @Override
    public AdviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_advice, parent, false);
        return new AdviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdviceViewHolder holder, int position) {
        AdviceItem adviceItem = adviceList.get(position);
        holder.bind(adviceItem);
    }

    @Override
    public int getItemCount() {
        return adviceList.size();
    }

    static class AdviceViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView contentTextView;

        public AdviceViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            contentTextView = itemView.findViewById(R.id.contentTextView);
        }

        public void bind(AdviceItem adviceItem) {
            titleTextView.setText(adviceItem.getTitle());
            contentTextView.setText(adviceItem.getContent());
        }
    }
}
