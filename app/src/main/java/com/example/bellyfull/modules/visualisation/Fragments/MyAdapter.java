package com.example.bellyfull.modules.visualisation.Fragments;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bellyfull.R;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    Context context;

    public MyAdapter(Context context, ArrayList<Baby> babyArrayList) {
        this.context = context;
        this.babyArrayList = babyArrayList;
    }

    ArrayList<Baby> babyArrayList;

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        Baby baby = babyArrayList.get(position);

        // Add logging statements to check the values
        Log.d("MyAdapter", "Fetal Length: " + baby.getFetalLength());
        Log.d("MyAdapter", "Fetal Weight: " + baby.getFetalWeight());
        Log.d("MyAdapter", "Head Circumference: " + baby.getHeadCircumference());

        holder.fetalLength.setText(String.valueOf(baby.getFetalLength()));
        holder.fetalWeight.setText(String.valueOf(baby.getFetalWeight()));
        holder.headCircumference.setText(String.valueOf(baby.getHeadCircumference()));
    }



    @Override
    public int getItemCount() {
        return babyArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView fetalLength, fetalWeight, headCircumference;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            fetalLength = itemView.findViewById(R.id.tvfetalLength);
            fetalWeight = itemView.findViewById(R.id.tvfetalWeight);
            headCircumference = itemView.findViewById(R.id.tvheadCircumference);
        }
    }
}
