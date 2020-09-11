package com.example.applicationtracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<ApplicationPOJO> mArrayList = new ArrayList<>();
    private CustomApplicationAdapter mAdapter;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db =FirebaseFirestore.getInstance();
//    private String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private CollectionReference applicationCollectionReference = db.collection("users").document().collection("applications");
    private ApplicationAdapter applicationAdapter;
    private static final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        fabSetup();
        recyclerViewSetup();
        prepareData();
    }




    private void prepareData() {
        ApplicationPOJO applications = null;
        applications = new ApplicationPOJO("1","11");
        mArrayList.add(applications);
        applications = new ApplicationPOJO("2","22");
        mArrayList.add(applications);
        applications = new ApplicationPOJO("3","33");
        mArrayList.add(applications);
        applications = new ApplicationPOJO("4","44");
        mArrayList.add(applications);
        applications = new ApplicationPOJO("5","55");
        mArrayList.add(applications);
        applications = new ApplicationPOJO("6","66");
        mArrayList.add(applications);
        applications = new ApplicationPOJO("7","77");
        mArrayList.add(applications);
        mAdapter.notifyDataSetChanged();
    }






    private void fabSetup(){
        FloatingActionButton fab;
        //Casting floating action button to respective ID
        fab = findViewById(R.id.application_floating_action_button);
        //Determines whether the floating action button is null or not and then proceed to set the OnClickListener
        if (fab != null)
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    customCalorieDialogBuilder();
                }
            });
    }





    private void customCalorieDialogBuilder(){
        final android.app.AlertDialog dialogBuilder = new android.app.AlertDialog.Builder(MainActivity.this).create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_application_dialog, null);
        final EditText companyNameEditText = dialogView.findViewById(R.id.companyNameEditText);
        final EditText jobNameEditText = dialogView.findViewById(R.id.jobNameEditText);
        Button buttonSubmit = dialogView.findViewById(R.id.buttonSubmit);
        Button buttonCancel = dialogView.findViewById(R.id.buttonCancel);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
            }
        });
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String companyNameValue = String.valueOf(companyNameEditText.getText());
                String jobNameValue = String.valueOf(jobNameEditText.getText());
                if (companyNameValue.trim().isEmpty() || jobNameValue.trim().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please insert acceptable value", Toast.LENGTH_SHORT).show();
                }
                else {
                    applicationCollectionReference.add(new ApplicationPOJO(companyNameValue,jobNameValue));
                    Toast.makeText(getApplicationContext(), "New application added", Toast.LENGTH_SHORT).show();
                    dialogBuilder.dismiss();
                }

            }
        });
        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }







    private void recyclerViewSetup() {
        Query query = applicationCollectionReference.orderBy("companyName", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<ApplicationPOJO> options = new FirestoreRecyclerOptions.Builder<ApplicationPOJO>()
                .setQuery(query, ApplicationPOJO.class)
                .build();
        applicationAdapter = new ApplicationAdapter(options);
        RecyclerView mRecyclerView1 = findViewById(R.id.applicationRecyclerView);
        mAdapter = new CustomApplicationAdapter(mArrayList, new OnApplicationsClickListener() {
            @Override
            public void onSettingsViewItemClicked(int position, int id) {
                switch(position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
            }
        });
        mRecyclerView1.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView1.setItemAnimator( new DefaultItemAnimator());
        mRecyclerView1.setAdapter(mAdapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                applicationAdapter.deleteItem(viewHolder.getAdapterPosition());
            }

        }).attachToRecyclerView(mRecyclerView1);
    }



}