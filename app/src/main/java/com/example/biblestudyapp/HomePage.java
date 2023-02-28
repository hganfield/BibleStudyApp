package com.example.biblestudyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        ImageButton groupButton = (ImageButton) findViewById(R.id.GroupButton);

        // when groupButton from homepage is clicked, the UI for managing groups is opened
        groupButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, ManageGroupUI.class));
            }
        });

        ImageButton AccountButton = (ImageButton) findViewById(R.id.AccountButton);

        // when groupButton from homepage is clicked, the UI for managing user account is opened
        groupButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, ManageGroupUI.class));
            }
        });
    }
}