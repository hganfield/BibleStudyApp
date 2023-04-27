package com.example.biblestudyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class CreateAccount extends AppCompatActivity {

    /*
    Ensures the Email and Password are valid
     */
    private FirebaseAuth authentication;
    /*
    The Email Address typed in by the user
     */
    private EditText EmailText;
    /*
    The Username typed in by the user
     */
    private EditText Username;

    /*
    The Password typed in by the user
     */
    private EditText PasswordText;

    /*
    The phone number typed in by the user
     */
    private EditText PhoneText;

    /*
    Reference to the Firebase database
     */
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createaccount);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        authentication = FirebaseAuth.getInstance();

        Username = findViewById(R.id.Username);
        EmailText = findViewById(R.id.Address);
        PasswordText = findViewById(R.id.Password);
        PhoneText = findViewById(R.id.Phone);

        Button previous = (Button) findViewById(R.id.Previous);

        previous.setOnClickListener(new View.OnClickListener() {
            /*
            The Previous Button and what it does when clicked
             */
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button create = (Button) findViewById(R.id.CreateAccount);

        create.setOnClickListener(new View.OnClickListener() {
            /*
            The Create Account button and what it does when clicked
             */
            @Override
            public void onClick(View view) {
                authentication.createUserWithEmailAndPassword(EmailText.getText().toString(),PasswordText.getText().toString()).addOnCompleteListener(CreateAccount.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                  setResult(14);
                                  Intent intent;
                                  intent = new Intent();
                                  User user = new User(FirebaseAuth.getInstance().getUid(),Username.getText().toString(),EmailText.getText().toString(), PhoneText.getText().toString());
                                  mDatabase.child("users").child(FirebaseAuth.getInstance().getUid()).setValue(user);
                                  mDatabase.child("users").child(FirebaseAuth.getInstance().getUid()).child("groups").setValue(new ArrayList<Group>());
                                  finish();

                                }
                                else{
                                    Toast.makeText(CreateAccount.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                );
            }
        });

    }

}