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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateAccount extends AppCompatActivity {

    private FirebaseAuth authentification;

    private EditText EmailText;

    private EditText PasswordText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createaccount);

        authentification = FirebaseAuth.getInstance();

        EmailText = findViewById(R.id.Address);
        PasswordText = findViewById(R.id.Password);

        Button create = (Button) findViewById(R.id.CreateAccount);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authentification.createUserWithEmailAndPassword(EmailText.toString(),PasswordText.toString()).addOnCompleteListener(CreateAccount.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                  Intent intent = getIntent();
                                  Intent result = new Intent();
                                  result.putExtra("user",authentification.getCurrentUser());
                                  setResult(14, result);
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