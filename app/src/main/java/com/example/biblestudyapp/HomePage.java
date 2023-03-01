package com.example.biblestudyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomePage extends AppCompatActivity {

    private TextView name;
    private FirebaseUser user;

    private DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Intent intent = getIntent();
        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference();
       if(user != null) {
            String username1 = database.child("users").child(user.getUid()).child("username").getKey();
            name = findViewById(R.id.DisplayName);
            name.setText(username1);
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
        ImageButton accountButton = (ImageButton) findViewById(R.id.AccountButton);

        // when groupButton from homepage is clicked, the UI for managing user account is opened
        accountButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, AccountPage.class));
            }
        });

        ImageButton bibleButton = (ImageButton) findViewById(R.id.BibleButton);

        // when groupButton from homepage is clicked, the UI for managing user account is opened
        bibleButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, BiblePage.class));
            }
        });
        Button signout = (Button) findViewById(R.id.Signout);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        });
    }
}