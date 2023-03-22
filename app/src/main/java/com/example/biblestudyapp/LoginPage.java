package com.example.biblestudyapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginPage extends AppCompatActivity {

    /*
    Used to authorize email and password
     */
    private FirebaseAuth auth;
    /*
    The Email address the user types in
     */
    private EditText Email;
    /*
    The Password the user types in
     */
    private EditText Password;

    ActivityResultLauncher<Intent> activitiyLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == 14){
                if(auth.getCurrentUser() != null) {
                    Intent startup = new Intent(LoginPage.this,HomePage.class);
                    startActivity(startup);
                }
                else{
                    Toast.makeText(LoginPage.this,"User is Null",Toast.LENGTH_SHORT).show();
                }

            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        auth = FirebaseAuth.getInstance();

        Email = findViewById(R.id.editTextTextEmailAddress2);
        Password = findViewById(R.id.editTextTextPassword);


        Button login = (Button) findViewById(R.id.buttonLogin);
        Button createAcc = (Button) findViewById(R.id.buttonCreate);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            /*
            *  The login button and what is does when clicked
             */
            public void onClick(View view) {
                auth.signInWithEmailAndPassword("mmark9293@gmail.com","123456789").addOnCompleteListener(LoginPage.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //FirebaseUser user = auth.getCurrentUser();
                            Intent intent = new Intent(LoginPage.this,HomePage.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(LoginPage.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        createAcc.setOnClickListener(new View.OnClickListener() {
            /*
             * The create account button and what it does
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPage.this,CreateAccount.class);
                activitiyLauncher.launch(intent);
            }
        });

    }
}