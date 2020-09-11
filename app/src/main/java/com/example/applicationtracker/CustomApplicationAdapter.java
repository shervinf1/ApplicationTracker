package com.example.applicationtracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomApplicationAdapter extends RecyclerView.Adapter<CustomApplicationAdapter.MyViewHolder> {
    // declaring some fields.
    private ArrayList<ApplicationPOJO> applicationsList;
    private OnApplicationsClickListener listener;


    public CustomApplicationAdapter(ArrayList<ApplicationPOJO> applicationsList, OnApplicationsClickListener listener) {
        this.listener = listener;
        this.applicationsList = applicationsList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView companyName, jobName;

        public MyViewHolder(final View itemView) {
            super(itemView);
            companyName = itemView.findViewById(R.id.companyNameTextView);
            jobName = itemView.findViewById(R.id.jobNameTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onSettingsViewItemClicked(getAdapterPosition(),itemView.getId());
                }
            });
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.application_list_layout, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        ApplicationPOJO settings = applicationsList.get(position);
        holder.companyName.setText(settings.getCompanyName());
        holder.jobName.setText(settings.getJobName());
    }

    @Override
    public int getItemCount() {
        return applicationsList.size();
    }

}
