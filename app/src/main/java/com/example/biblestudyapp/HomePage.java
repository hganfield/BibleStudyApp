package com.example.biblestudyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePage extends AppCompatActivity {

    private TextView name;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Intent intent = getIntent();
        user = FirebaseAuth.getInstance().getCurrentUser();
       // FirebaseAuth user = intent.getParcelableExtra("main-user");
       if(user != null) {
            String username = user.getEmail();
            name = findViewById(R.id.DisplayName);
            name.setText(username);
        }
        else{
            Toast.makeText(HomePage.this,"User is Null",Toast.LENGTH_SHORT).show();

        }


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
                startActivity(new Intent(HomePage.this, BiblePage.class));
            }
        });
    }
}