package com.example.biblestudyapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static UserProfileFragment newInstance(String param1, String param2) {
        UserProfileFragment fragment = new UserProfileFragment();
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

    private TextView username;

    private SharedViewModel sharedViewModel;

    private Button add_or_delete;

    private boolean add;

    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        add = true;
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        username = view.findViewById(R.id.username);
        add_or_delete = (Button) view.findViewById(R.id.addButton);
        Bundle bundle = getArguments();
        String userId = bundle.getString("userId");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        System.out.println("First");

        ref.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                if(sharedViewModel.containsUser(user)){
                    System.out.println("Set to delete");
                    add_or_delete.setText("Delete");
                    add = false;
                }
                username.setText(user.getUsername());
                add_or_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(add){
                            System.out.println("Adding to list");
                            addUserToList(user);
                        }
                        else{
                            System.out.println("Deleting list");
                            deleteUser(user);
                        }
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.popBackStack();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }

    /*
    Adds a user to the Mutable list that is shared between the InviteUserFragment and the UserProfile
     */
    public void addUserToList(User user){
        List<User> userList = sharedViewModel.getUserList().getValue();
        if(userList == null){
            userList = new ArrayList<>();
            System.out.println("Creating New List");
        }
        System.out.println("Adding User");
        userList.add(user);
        sharedViewModel.setUserList(userList);
    }

    public void deleteUser(User user){
        List<User> userList = sharedViewModel.getUserList().getValue();
        if(userList != null) {
            if(userList.remove(user)){
                System.out.println("Success");
            }
            sharedViewModel.setUserList(userList);
        }
    }

}