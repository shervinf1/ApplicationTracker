package com.example.applicationtracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<ApplicationPOJO> mArrayList = new ArrayList<>();
    private ApplicationAdapter mAdapter;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db =FirebaseFirestore.getInstance();
    private CollectionReference applicationCollectionReference = db.collection("applications");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        fabSetup();
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
                    startActivity(new Intent(MainActivity.this, NewApplicationActivity.class));
                }
            });
        recyclerViewSetup();
    }






    private void recyclerViewSetup() {
        Query query = applicationCollectionReference.orderBy("companyName", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<ApplicationPOJO> options = new FirestoreRecyclerOptions.Builder<ApplicationPOJO>()
                .setQuery(query, ApplicationPOJO.class)
                .build();

        mAdapter = new ApplicationAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.applicationRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                mAdapter.deleteItem(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);

        mAdapter.setOnItemClickListener(new ApplicationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                ApplicationPOJO applicationPOJO = documentSnapshot.toObject(ApplicationPOJO.class);
                String id = documentSnapshot.getId();
                String path = documentSnapshot.getReference().getPath();
                Toast.makeText(MainActivity.this,
                        "Position: " + position + " ID: " + id, Toast.LENGTH_SHORT).show();
                Intent viewSelectedApplicationIntent = new Intent(MainActivity.this, ViewSelectedApplication.class);
                assert applicationPOJO != null;
                viewSelectedApplicationIntent.putExtra("companyName", applicationPOJO.getCompanyName());
                viewSelectedApplicationIntent.putExtra("jobName", applicationPOJO.getJobName());
                viewSelectedApplicationIntent.putExtra("description", applicationPOJO.getDescription());
                finish();
                startActivity(viewSelectedApplicationIntent);
            }
        });
    }





    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }
}