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
    private OnClickListener onClickListener;  // Use custom interface, not View.OnClickListener

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
        holder.customerPolicyNo.setText(examItem.getPolicyNo());
        holder.customerDueDate.setText(examItem.getNextDueDate());

        // Set OnClickListener properly
        holder.itemView.setOnClickListener(view -> {
            if (onClickListener != null) {
                onClickListener.onClick(position, examItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return examList.size();
    }

    // Corrected method to set click listener
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    // Corrected interface definition
    public interface OnClickListener {
        void onClick(int position, CustomerClass customer);
    }

    static class CustomerRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView examName, customerPolicyNo, customerDueDate;

        public CustomerRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            examName = itemView.findViewById(R.id.customerName);
            customerPolicyNo = itemView.findViewById(R.id.customerPolicyNo);
            customerDueDate = itemView.findViewById(R.id.customerDueDate);
        }
    }
}
