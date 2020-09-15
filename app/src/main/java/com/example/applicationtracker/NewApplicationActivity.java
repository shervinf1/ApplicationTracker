package com.example.applicationtracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class NewApplicationActivity extends AppCompatActivity {
    private EditText editTextCompanyName;
    private EditText editTextJobName;
    private EditText editTextDescription;
    private FirebaseFirestore db =FirebaseFirestore.getInstance();
    private String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private CollectionReference applicationCollectionReference = db.collection("users").document(userID).collection("applications");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_application);
        toolbarSetup();

        editTextCompanyName = findViewById(R.id.edit_text_company_name);
        editTextJobName = findViewById(R.id.edit_text_job_name);
        editTextDescription = findViewById(R.id.edit_text_description);
    }



    private void saveNewApplication() {
        String companyName = editTextCompanyName.getText().toString();
        String jobName = editTextJobName.getText().toString();
        String description = editTextDescription.getText().toString();

        if (companyName.trim().isEmpty() || description.trim().isEmpty() || jobName.trim().isEmpty()) {
            Toast.makeText(NewApplicationActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        applicationCollectionReference.add(new ApplicationPOJO(companyName, jobName, description));
        Toast.makeText(NewApplicationActivity.this, "Application added", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void toolbarSetup(){
        Toolbar mToolbar = findViewById(R.id.newApplicationToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivityIntent = new Intent(NewApplicationActivity.this,MainActivity.class);
                finish();
                startActivity(mainActivityIntent);
            }
        });
    }


    public void AddApplication(View v)
    {
        saveNewApplication();
    }

}