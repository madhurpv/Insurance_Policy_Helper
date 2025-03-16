package com.example.licpolicyhelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BirthdayRecyclerViewAdapter extends RecyclerView.Adapter<BirthdayRecyclerViewAdapter.BirthdayRecyclerViewHolder> {

    private List<BirthdayDetailsClass> birthdaysList;
    private OnClickListener onClickListener;  // Use custom interface, not View.OnClickListener

    // Constructor
    public BirthdayRecyclerViewAdapter(List<BirthdayDetailsClass> birthdaysList) {
        this.birthdaysList = birthdaysList;
    }

    @NonNull
    @Override
    public BirthdayRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.birthday_recyclerview_card, parent, false);
        return new BirthdayRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BirthdayRecyclerViewHolder holder, int position) {
        BirthdayDetailsClass birthdayItem = birthdaysList.get(position);
        holder.birthdayUserName.setText(birthdayItem.getName());
        holder.userPhoneNo.setText(String.valueOf(birthdayItem.getPhoneNo()));
        holder.userBirthDate.setText(birthdayItem.getBirthDate());

        // Set OnClickListener properly
        holder.itemView.setOnClickListener(view -> {
            if (onClickListener != null) {
                onClickListener.onClick(position, birthdayItem);
            }
        });
    }

    /*public void filterList(ArrayList<BirthdayDetailsClass> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        //examList = filterlist;
        birthdaysList.clear();
        birthdaysList.addAll(filterlist);
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }*/

    @Override
    public int getItemCount() {
        return birthdaysList.size();
    }

    // Corrected method to set click listener
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    // Corrected interface definition
    public interface OnClickListener {
        void onClick(int position, BirthdayDetailsClass customer);
    }

    static class BirthdayRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView birthdayUserName, userPhoneNo, userBirthDate;

        public BirthdayRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            birthdayUserName = itemView.findViewById(R.id.birthdayUserName);
            userPhoneNo = itemView.findViewById(R.id.userPhoneNo);
            userBirthDate = itemView.findViewById(R.id.userBirthDate);
        }
    }
}
