package com.example.biblestudyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;


import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {

    private TextView name;
    private FirebaseUser user;

    private DatabaseReference database;

    private boolean isActionBarVisible = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        //NavController navController = NavHostFragment.findNavController(this);



        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference();
       if(user != null){
           database.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {
                   String username1 = snapshot.child("users").child(user.getUid()).getValue(User.class).getUsername();

                   //name = findViewById(R.id.welcome);
                   //name.setText("Welcome " + username1);
               }
               @Override
               public void onCancelled(@NonNull DatabaseError error) {

               }
           });
        }
        else{
            Toast.makeText(HomePage.this,"User is Null",Toast.LENGTH_SHORT).show();

        }


        BottomNavigationView view = findViewById(R.id.bottomNavigationView);
        Fragment Fbible = new BibleFragment();
        Fragment Fgroup = new GroupFragment();
        Fragment Fprofile = new ProfileFragment();
        Fragment Fhome = new HomeFragment();

        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
                        .add(R.id.container,Fhome,null)
                                .commit();
        view.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case(R.id.bible):
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,Fbible)
                                .setReorderingAllowed(true)
                                .addToBackStack("name")
                                .commit();
                        return true;
                    case(R.id.group) :
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,Fgroup)
                                .setReorderingAllowed(true)
                                .addToBackStack("name").commit();
                        return true;
                    case(R.id.profile):
                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.container,Fprofile)
                                .addToBackStack("name")
                                .commit();
                        return true;
                    case(R.id.home):
                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.container,Fhome)
                                .addToBackStack("name")
                                .commit();
                        return true;

                }
                return false;
            }
        });

        //Button signout = (Button) findViewById(R.id.signout);
        //signout.setOnClickListener(new View.OnClickListener() {
         //   @Override
           // public void onClick(View view) {
             //   FirebaseAuth.getInstance().signOut();
               // finish();
            //}
        //});
    }
    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("HomeResume");
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (currentFragment instanceof HomeFragment && isActionBarVisible) {
            if (toolbar != null) {
                ConstraintLayout constraintLayout = findViewById(R.id.Menu);
                constraintLayout.setVisibility(View.VISIBLE);
            } else {
                ConstraintLayout constraintLayout = findViewById(R.id.Menu);
                constraintLayout.setVisibility(View.GONE);
            }

        }
    }

    public void setActionBarVisible(boolean isVisible) {
        isActionBarVisible = isVisible;
    }
}