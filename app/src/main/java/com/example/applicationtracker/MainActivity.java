package com.example.applicationtracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
    private SharedPreferences sp;
    private FirebaseFirestore db =FirebaseFirestore.getInstance();
    private String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private CollectionReference applicationCollectionReference = db.collection("users").document(userID).collection("applications");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences("logged", Context.MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();
        toolbarSetup();
        fabSetup();
    }

    private void toolbarSetup(){
        Toolbar mToolbar = findViewById(R.id.mainActivityToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertSignout();
            }
        });
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item){
//        switch(item.getItemId()){
//            case R.id.logout:   //this item has your app icon
//                Toast.makeText(this,"Logged Out",Toast.LENGTH_SHORT).show();
//                return true;
//            default: return super.onOptionsItemSelected(item);
//        }
//    }




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


    private void alertSignout() {
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(MainActivity.this);
        // Setting Dialog Title
        alertDialog2.setTitle("Confirm Sign Out");
        // Setting Dialog Message
        alertDialog2.setMessage("Are you sure you want to Sign out?");
        // Setting Positive "Yes" Btn
        alertDialog2.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        sp.edit().putBoolean("logged",false).apply();
                        Intent i = new Intent(MainActivity.this, LoginActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }
                });
        // Setting Negative "NO" Btn
        alertDialog2.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        // Showing Alert Dialog
        alertDialog2.show();
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