package com.example.applicationtracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ApplicationAdapter extends FirestoreRecyclerAdapter<ApplicationPOJO, ApplicationAdapter.ApplicationHolder> {

    public ApplicationAdapter(@NonNull FirestoreRecyclerOptions<ApplicationPOJO> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ApplicationHolder applicationHolder, int i, @NonNull ApplicationPOJO applicationPOJOPOJO) {
        applicationHolder.companyNameTextView.setText(applicationPOJOPOJO.getCompanyName());
        applicationHolder.jobNameTextView.setText(applicationPOJOPOJO.getJobName());
    }

    @NonNull
    @Override
    public ApplicationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.application_list_layout,parent,false);
        return new ApplicationHolder(v);
    }

    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class ApplicationHolder extends RecyclerView.ViewHolder{
        TextView companyNameTextView;
        TextView jobNameTextView;

        public ApplicationHolder(@NonNull View itemView) {
            super(itemView);
            companyNameTextView = itemView.findViewById(R.id.companyNameTextView);
            jobNameTextView = itemView.findViewById(R.id.jobNameTextView);
        }
    }
}


