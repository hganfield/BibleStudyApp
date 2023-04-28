package com.example.biblestudyapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class PrayerRequestFormActivity extends AppCompatActivity {

    private View view;

    private DatabaseReference database;

    private String userid;

    private User user;

    private Group group;

    private String groupid;


    private PrayerRequest pr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prayer_request_form);

        userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.database = FirebaseDatabase.getInstance().getReference();

        DatabaseReference userref = FirebaseDatabase.getInstance().getReference("user");


        userref.child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
    });

        Spinner groupSelection = findViewById(R.id.GroupSelection);

        List<String> usersGroups = user.getGroups();

        // TODO display group name not id
        ArrayAdapter<String> usersGroupsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, usersGroups);
        groupSelection.setAdapter(usersGroupsAdapter);


        groupSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //TODO
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // groupid = parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button submit = findViewById(R.id.PrayerSubmit);
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                save();
            }
        });

        FloatingActionButton backbutton = (FloatingActionButton) findViewById(R.id.backButton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Do you wish to leave without saving?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        save();
                        finish();
                    }
                });

                // Display the dialog box
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }


    public void save(){
        EditText titleEditText = (EditText) findViewById(R.id.PrayerRequestTitle);
        String titlePrayerRequest = titleEditText.getText().toString();

        EditText textEditText = (EditText) findViewById(R.id.PrayerRequestText);
        String textPrayerRequest = textEditText.getText().toString();


        // TODO
        //display prayer request list in a group's homepage



        userid = FirebaseAuth.getInstance().getUid();
        groupid = "test";
        // String groupId = FirebaseDatabase.getInstance().getReference("group").child(groupid);
        pr = new PrayerRequest(titlePrayerRequest, textPrayerRequest, userid, groupid);
        // add to user's list of prayer requests??
        // TODO
        // add to a group's list of prayer requests!!

        // TODO add pr to database
        // does the below line get the right pr object?
        DatabaseReference prayerRequestRef = FirebaseDatabase.getInstance().getReference("prayer");

        prayerRequestRef.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // TODO
                pr = snapshot.getValue(PrayerRequest.class);
                pr.updateDateTime();
                prayerRequestRef.child(userid).setValue(userid);
                prayerRequestRef.setValue(groupid);
                prayerRequestRef.setValue(pr.getDate());
                prayerRequestRef.setValue(pr.getTime());
                prayerRequestRef.setValue(pr.getText());
                prayerRequestRef.setValue(pr.getTitle());
                //add pr to database
                // have to get stuff from here, can add stuff elsewhere
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Error querying prayer requests", error.toException());
            }
        });


    }
}