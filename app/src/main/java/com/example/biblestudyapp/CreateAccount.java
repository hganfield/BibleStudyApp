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
                                  User user = new User(FirebaseAuth.getInstance().getUid(),Username.getText().toString(),EmailText.getText().toString(), PhoneText.getText().toString(), "data:image/jpeg;base64,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" );
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