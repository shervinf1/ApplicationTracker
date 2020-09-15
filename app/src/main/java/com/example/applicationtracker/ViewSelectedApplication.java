package com.example.applicationtracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ViewSelectedApplication extends AppCompatActivity {
    private TextView companyNameTextView;
    private TextView jobNameTextView;
    private TextView descriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_selected_application);
        toolbarSetup();
        companyNameTextView = findViewById(R.id.selectedCompanyNameTextView);
        jobNameTextView = findViewById(R.id.selectedJobNameTextView);
        descriptionTextView = findViewById(R.id.selectedDescriptionTextView);


        Intent intent = getIntent();
        String cName = intent.getStringExtra("companyName");
        String jName = intent.getStringExtra("jobName");
        String d = intent.getStringExtra("description");

        companyNameTextView.setText(cName);
        jobNameTextView.setText(jName);
        descriptionTextView.setText(d);
    }

    //Method that displays back button in toolbar and ends this activity when button is clicked.
    public void toolbarSetup() {
        Toolbar mToolbar = findViewById(R.id.mainActivityToolbar);
        TextView mToolbarTextView = findViewById(R.id.viewSelectedApplicationTextView);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Your code
                Intent mainActivityIntent = new Intent(ViewSelectedApplication.this, MainActivity.class);
                finish();
                startActivity(mainActivityIntent);
            }
        });
    }


}