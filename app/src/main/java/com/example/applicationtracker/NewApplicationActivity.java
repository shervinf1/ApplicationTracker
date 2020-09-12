package com.example.applicationtracker;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class NewApplicationActivity extends AppCompatActivity {
    private EditText editTextCompanyName;
    private EditText editTextJobName;
    private EditText editTextDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_application);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic__close_24);
        setTitle("Add Application");

        editTextCompanyName = findViewById(R.id.edit_text_company_name);
        editTextJobName = findViewById(R.id.edit_text_job_name);
        editTextDescription = findViewById(R.id.edit_text_description);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.new_application_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {
        String companyName = editTextCompanyName.getText().toString();
        String jobName = editTextJobName.getText().toString();
        String description = editTextDescription.getText().toString();

        if (companyName.trim().isEmpty() || description.trim().isEmpty() || jobName.trim().isEmpty()) {
            Toast.makeText(NewApplicationActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        CollectionReference applicationRef = FirebaseFirestore.getInstance()
                        .collection("applications");
        applicationRef.add(new ApplicationPOJO(companyName, jobName, description));
        Toast.makeText(NewApplicationActivity.this, "Application added", Toast.LENGTH_SHORT).show();
        finish();
    }
}