package com.example.biblestudyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GroupHomePage extends AppCompatActivity {

    private DatabaseReference group_ref;

    private DatabaseReference group_journal_ref;

    private TextView group_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_home_page);

        Intent intent = getIntent();

        //group_name = findViewById(R.id.group_name_box);
        String groupId = intent.getStringExtra("group_page_id");
        group_ref = FirebaseDatabase.getInstance().getReference("groups").child(groupId);
        group_journal_ref = FirebaseDatabase.getInstance().getReference("journal").child(groupId);

        Bundle bundle = new Bundle();
        bundle.putString("id",groupId);
        BibleFragment receiverFragment = new BibleFragment();
        receiverFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.bible_container, receiverFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();





    }
}