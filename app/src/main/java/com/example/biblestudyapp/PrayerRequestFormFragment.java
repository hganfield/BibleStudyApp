package com.example.biblestudyapp;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrayerRequestFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrayerRequestFormFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PrayerRequestFormFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PrayerRequestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PrayerRequestFormFragment newInstance(String param1, String param2) {
        PrayerRequestFormFragment fragment = new PrayerRequestFormFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    private View view;

    private DatabaseReference database;

    private FirebaseUser user;

    private Activity dummy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_prayer_request_form, container, false);
        this.view = view;

        this.user = FirebaseAuth.getInstance().getCurrentUser();
        this.database = FirebaseDatabase.getInstance().getReference();

        Spinner groupSelection = view.findViewById(R.id.GroupSelection);

        List<String> usersGroups = database.getInstance().getCurrentUser().child(groups);
        //TODO
        //need to set the onSelectionListener or whatever for the groups drop down menu

        Button submit = view.findViewById(R.id.PrayerSubmit);
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                save();
            }
        });

        FloatingActionButton backbutton = (FloatingActionButton) view.findViewById(R.id.backButton);
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
        return view;
    }

    public void save(){
        EditText titleEditText = (EditText) view.findViewById(R.id.PrayerRequestTitle);
        String titlePrayerRequest = titleEditText.getText().toString();

        EditText textEditText = (EditText) view.findViewById(R.id.PrayerRequestText);
        String textPrayerRequest = textEditText.getText().toString();


        // TODO
        //display prayer request list in a group's homepage
        // add button to add a prayer request


        String userId = FirebaseAuth.getInstance().getUid();
        String groupId = FirebaseDatabase.getInstance().getReference("group").child(groupId);
        PrayerRequest newPR = new PrayerRequest(titlePrayerRequest, textPrayerRequest, userId, groupId);
        // add to user's list of prayer requests??
        // TODO
        // add to a group's list of prayer requests!!


        DatabaseReference prayerRequestref = FirebaseDatabase.getInstance().getReference("prayer");

        prayerRequestref.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // TODO
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