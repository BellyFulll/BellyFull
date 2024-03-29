package com.example.bellyfull.modules.AdviceAndTips.Adapter;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
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
            // Apply custom styling for the title
            titleTextView.setTypeface(ResourcesCompat.getFont(titleTextView.getContext(), R.font.a_bee_zee_regular));
            titleTextView.setTextColor(ContextCompat.getColor(titleTextView.getContext(), R.color.black));
            titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

            // Set data to your views
            titleTextView.setText(adviceItem.getTitle());
            contentTextView.setText(adviceItem.getContent());
        }
    }
}