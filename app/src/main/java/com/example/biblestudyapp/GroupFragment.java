package com.example.biblestudyapp;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 * The Group Fragment that will have all the UI for the group Aspect of the project
 * Within this Fragment users can create groups, join groups, and visit a specific groups home page
 */
public class GroupFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /*
    The current user logged into the app
     */
    private FirebaseUser user;
    /*
    The reference to the group table in FireBase's real time database
     */
    private DatabaseReference groupDatabase;

    /*
    The List of all the user Id's to be added to the group
     */
    private List<String> groupList;

    /*
    The View to display the list of groups the current user is in
     */
    private RecyclerView recyclerView;

    /*
    The Adapter that allows the group name to be shown on the list as well as any other useful objects
     */
    private GroupAdapter groupAdapter;

    public GroupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GroupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GroupFragment newInstance(String param1, String param2) {
        GroupFragment fragment = new GroupFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /*
    Retrieving the results of the Intents that jump from this fragment
     */
    ActivityResultLauncher<Intent> activitiyLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == 1){
                String groupId = result.getData().toString();
                groupList.add(groupId);
                refreshGroupList();

            }
        }
    });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        System.out.println("Resume");
        ((HomePage) getActivity()).setActionBarVisible(false);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group, container, false);

        //Initialize all the variables that will be used in this fragment
        Button create = (Button) view.findViewById(R.id.Cbutton1);

        user = FirebaseAuth.getInstance().getCurrentUser();
        groupDatabase = FirebaseDatabase.getInstance().getReference("users");
        recyclerView = view.findViewById(R.id.journal_list);
        groupList = new ArrayList<String>();

        refreshGroupList();
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GroupForm.class);
                activitiyLauncher.launch(intent);
            }
        });


        return view;
    }

    /*
    Refreshes the RecyclerView to have an updated view of all the groups the current user is in
     */
    public void refreshGroupList(){
        //Retrieving information from the database
        groupDatabase.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                System.out.println("Setting groupList");
                groupList = user.getGroups();
                if(groupList == null || groupList.isEmpty()){
                    System.out.println("Group List is empty");
                }
                else {
                    System.out.println("Here");
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    groupAdapter = new GroupAdapter(groupList,getContext());
                    recyclerView.setAdapter(groupAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



}