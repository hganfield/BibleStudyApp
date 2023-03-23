package com.example.biblestudyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountPage extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    private TextView name;

    private TextView email;

    FirebaseUser currentUser;

    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_page);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               User user = snapshot.child(currentUser.getUid()).getValue(User.class);

                name = findViewById(R.id.realUsername);
                name.setText(user.getUsername());
                email = findViewById(R.id.realEmail);
                email.setText(user.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}