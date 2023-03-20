package com.example.biblestudyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ManageGroupUI extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_group_ui);

        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference();
        /*
            The Code for adding a user to a new specific group with no people attached
            */
        //ArrayList<Group> a = new ArrayList<Group>();
        //a.add(new Group(null,"Software Team"));
        //a.add(new Group(null,"Cross Country Team"));
        //database.child("users").child(user.getUid()).child("groups").setValue(a);

    }
}