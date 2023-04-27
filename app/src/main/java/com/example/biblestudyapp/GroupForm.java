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

/**
 * GroupForm Activity
 * This is the Activity that manages the process of creating a group
 * The Activity will return with the user having created a new group
 */
public class GroupForm extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_form);

        //Setting the container to the first step in creating a group: The groupForm
        GroupFormFragment receiverFragment = new GroupFormFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.group_container, receiverFragment, null)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();

    }
}