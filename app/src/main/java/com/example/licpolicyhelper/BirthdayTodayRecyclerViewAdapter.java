package com.example.licpolicyhelper;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BirthdayTodayRecyclerViewAdapter extends RecyclerView.Adapter<BirthdayTodayRecyclerViewAdapter.BirthdayTodayRecyclerViewHolder> {

    private List<BirthdayDetailsClass> birthdaysList;
    //private OnClickListener onClickListener;  // Use custom interface, not View.OnClickListener

    // Constructor
    public BirthdayTodayRecyclerViewAdapter(List<BirthdayDetailsClass> birthdaysList) {
        this.birthdaysList = birthdaysList;
    }

    @NonNull
    @Override
    public BirthdayTodayRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.birthdaytoday_recyclerview_card, parent, false);
        return new BirthdayTodayRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BirthdayTodayRecyclerViewHolder holder, int position) {
        BirthdayDetailsClass birthdayItem = birthdaysList.get(position);
        holder.birthdayUserName.setText(birthdayItem.getName());
        holder.userPhoneNo.setText(String.valueOf(birthdayItem.getPhoneNo()));
        holder.msg1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //holder.userPhoneNo.setText("Hello!");
                PackageManager packageManager = holder.msg1Button.getContext().getPackageManager();
                Intent i = new Intent(Intent.ACTION_VIEW);
                //Toast.makeText(holder.msg1Button.getContext(), "Button1 clicked!", Toast.LENGTH_SHORT).show();

                try {
                    //String url = "https://api.whatsapp.com/send?phone="+ birthdayItem.getPhoneNo() +"&text=" + URLEncoder.encode("Hello", "UTF-8");

                    String url = "http://api.whatsapp.com/send?phone="+ birthdayItem.getPhoneNo() +"&text=" + URLEncoder.encode(getMessage1(holder.msg1Button.getContext(), birthdayItem.getName()), "UTF-8");
                    i.setPackage("com.whatsapp");
                    i.setData(Uri.parse(url));
                    if (i.resolveActivity(packageManager) != null) {
                        holder.msg1Button.getContext().startActivity(i);
                        Log.d("QWER", "No Error!!");
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    Log.d("QWER", "Error!!");
                }
            }
        });
        holder.msg2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PackageManager packageManager = holder.msg2Button.getContext().getPackageManager();
                Intent i = new Intent(Intent.ACTION_VIEW);

                try {
                    String url = "https://api.whatsapp.com/send?phone="+ birthdayItem.getPhoneNo() +"&text=" + URLEncoder.encode(getMessage2(holder.msg2Button.getContext(), birthdayItem.getName()), "UTF-8");
                    i.setPackage("com.whatsapp");
                    i.setData(Uri.parse(url));
                    if (i.resolveActivity(packageManager) != null) {
                        holder.msg2Button.getContext().startActivity(i);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        // Set OnClickListener properly
        /*holder.itemView.setOnClickListener(view -> {
            if (onClickListener != null) {
                onClickListener.onClick(position, birthdayItem);
            }
        });*/
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
    /*public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }*/

    // Corrected interface definition
    /*public interface OnClickListener {
        void onClick(int position, BirthdayDetailsClass customer);
    }*/

    static class BirthdayTodayRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView birthdayUserName, userPhoneNo;
        Button msg1Button, msg2Button;

        public BirthdayTodayRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            birthdayUserName = itemView.findViewById(R.id.birthdayUserName);
            userPhoneNo = itemView.findViewById(R.id.userPhoneNo);
            msg1Button = itemView.findViewById(R.id.msg1Button);
            msg2Button = itemView.findViewById(R.id.msg2Button);
        }
    }

    public String getMessage1(Context context, String name){
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPreferences", MODE_PRIVATE);
        String message = sharedPreferences.getString("message1", "message1");

        String currentDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        message = message.replace("<name>", name).replace("<date>", currentDate);

        return message;
    }

    public String getMessage2(Context context, String name){
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPreferences", MODE_PRIVATE);
        String message = sharedPreferences.getString("message2", "message2");

        String currentDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        message = message.replace("<name>", name).replace("<date>", currentDate);

        return message;
    }
}
