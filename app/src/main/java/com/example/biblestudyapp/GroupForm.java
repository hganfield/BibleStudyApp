package com.example.biblestudyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;


public class GroupForm extends AppCompatActivity {

    private TextInputEditText groupName;

    private Switch isPrivate;

    private TextInputEditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_form);

        groupName = findViewById(R.id.group_name);
        password = findViewById(R.id.password);
        isPrivate = findViewById(R.id.publicGroup);

        isPrivate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    password.setVisibility(View.VISIBLE);
                }
                else{
                    password.setVisibility(View.GONE);
                }

            }
        });

        Button addMems = (Button) findViewById(R.id.addMembers);
        addMems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Take information from the fragment and store in database
                String name = groupName.getText().toString();
                String passwordtxt = password.getText().toString();
                boolean pbool = isPrivate.isChecked();

                if(name.equals("")){
                    Toast.makeText(GroupForm.this, "Please Enter A Group Name",
                            Toast.LENGTH_SHORT).show();

                }else if(pbool && passwordtxt.equals("")) {

                    Toast.makeText(GroupForm.this, "Please Enter A Password",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    Bundle bundle = new Bundle();
                    bundle.putString("name",name);
                    bundle.putBoolean("private",pbool);
                    if(pbool){
                        bundle.putString("password",passwordtxt);
                    }

                    //Open new fragment to invite users to the group

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.InviteUsers, InviteUsersFragment.class, null)
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
    }
}