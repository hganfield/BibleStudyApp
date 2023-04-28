package com.example.biblestudyapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.biblestudyapp.Journal;
import com.example.biblestudyapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

public class JournalForm extends AppCompatActivity {

    private TextView verseReference;

    private EditText title;

    private EditText journalText;

    private String ref;
    private String verse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_form);
        verse = getIntent().getStringExtra("verse");
        ref = getIntent().getStringExtra("db_ref");
        String actual_ref = getIntent().getStringExtra("chapter_ref");
        verseReference = findViewById(R.id.ChapterVerseReference);
        verseReference.setText(actual_ref);
        journalText = findViewById(R.id.JournalText);
        title = findViewById(R.id.JournalTitle);

        Button submit = (Button) findViewById(R.id.JournalSubmit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
                finish();
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
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference journalref = FirebaseDatabase.getInstance().getReference("journal");

        journalref.child(userId).child(ref).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // The verse hasn't been journaled yet, create a new journal object and store it in the database
                String journalId = journalref.child(userId).child(ref).push().getKey();
                Date time = Calendar.getInstance().getTime();
                Journal journal = new Journal(title.getText().toString(), userId,verse,time);
                journal.setJournalText(journalText.getText().toString());
                journal.setTitle(title.getText().toString());
                journalref.child(userId).child(ref);
                journalref.child(userId).child(ref).child(journalId).setValue(journal);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Error querying journals for chapter: " + ref, error.toException());
            }
        });

    }
}