package com.example.licpolicyhelper;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomerRecyclerViewAdapter extends RecyclerView.Adapter<CustomerRecyclerViewAdapter.CustomerRecyclerViewHolder> {


    private List<CustomerClass> examList;

    // Constructor
    public CustomerRecyclerViewAdapter(List<CustomerClass> examList) {
        this.examList = examList;
    }



    @NonNull
    @Override
    public CustomerRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_recyclerview_card, parent, false);
        return new CustomerRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerRecyclerViewHolder holder, int position) {
        CustomerClass examItem = examList.get(position);

        holder.examName.setText(examItem.getName());
        //holder.examDate.setText(examItem.getDate());
        //holder.examMessage.setText(examItem.getMessage());
    }

    @Override
    public int getItemCount() {
        return examList.size();
    }




    static class CustomerRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView examName, examDate, examMessage;
        ImageView examPic, examPic2;

        public CustomerRecyclerViewHolder(@NonNull View itemView) {

            super(itemView);

            examName = itemView.findViewById(R.id.customerName);
            //examDate = itemView.findViewById(R.id.examDate);
            //examMessage = itemView.findViewById(R.id.examMessage);
            //examPic = itemView.findViewById(R.id.examPic);
            //examPic2 = itemView.findViewById(R.id.examPic2);
        }
    }

}
