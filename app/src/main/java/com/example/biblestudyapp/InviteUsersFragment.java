package com.example.biblestudyapp;

import static com.example.biblestudyapp.SearchUtility.getAllItems;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.ActionProvider;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
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
 * Use the {@link InviteUsersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InviteUsersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /*
    The List of userIds
     */
    private ArrayList<String> usersList;
    /*
    The Current User logged in
     */
    private FirebaseUser current_user;

    /*
    The listView object that lists out all the users
     */
    private ListView listView;

    /*
    The adapter that adapts a list of strings to the listView
     */
    private ArrayAdapter<String> adapter;

    /*
    The Context in which this fragment is running
     */
    private Context context;

    /*
    The reference to the user table of the database
     */
    private DatabaseReference user_reference;

    /*
    The reference to the group table of the database
     */
    private DatabaseReference group_ref;

    /*
    The SharedViewModel that allows multiple fragments to access the list of users that will be added to a group
     */
    private SharedViewModel sharedViewModel;


    public InviteUsersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InviteUsersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InviteUsersFragment newInstance(String param1, String param2) {
        InviteUsersFragment fragment = new InviteUsersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_invite_users, container, false);
        //Initializing all the variables
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        context = this.getContext();
        listView = view.findViewById(R.id.listView);
        current_user = FirebaseAuth.getInstance().getCurrentUser();
        user_reference = FirebaseDatabase.getInstance().getReference("users");
        group_ref = FirebaseDatabase.getInstance().getReference("groups");
        Button createGroup = (Button) view.findViewById(R.id.finish_group);
        Bundle bundle = getArguments();
        String g_name = bundle.getString("name");
        boolean privatebool = bundle.getBoolean("private");
        String password = bundle.getString("password");

        //Testing
        sharedViewModel.printList();

        getAllUsers();

        //Setting onClickListen for the createGroup button
        //Will check if there are users in the list if there are it will create a new group
        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<User> userList = sharedViewModel.getUserList().getValue();
                List<String> groupList = new ArrayList<>();
                String userId = current_user.getUid();
                FirebaseDatabase.getInstance().getReference("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        userList.add(snapshot.getValue(User.class));
                        String groupId = group_ref.push().getKey();
                        for(User u : userList){
                            groupList.add(u.getUid());
                            u.addGroup(groupId);
                            u.updateDB();
                        }
                        if(groupList == null ||groupList.isEmpty() || groupList.size() == 1){
                        }
                        else{

                            Group group;
                            if(privatebool){
                                group = new Group(groupList,g_name,groupId,privatebool,password);
                            }
                            else {
                                group = new Group(groupList, g_name, groupId, privatebool);
                            }
                            group_ref.child(groupId).setValue(group);
                            // Set the result data to include the newly created group
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("new_groupId", groupId);
                            getActivity().setResult(1,resultIntent);
                            getActivity().finish();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        });
        return view;
    }
    /*
    * Gets all of the users that have accounts and sets it to the listView via an adapter
    * @POSTCONDITION: The listView is set with all the users
     */
    private void getAllUsers() {
        user_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    User user = dataSnapshot1.getValue(User.class);
                    if(user.getUid() != null && !user.getUid().equals(current_user.getUid())) {
                        //user.getUsername();
                        //Toast.makeText(InviteUsersFragment.this.getContext(), user.getUsername(), Toast.LENGTH_LONG).show();
                        usersList.add(user.getUsername()+ "|" + user.getUid());
                    }
                }
                if(usersList.isEmpty()){
                   Toast.makeText(InviteUsersFragment.this.getContext(), "Empty", Toast.LENGTH_LONG).show();

                }
                adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,usersList){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        //Makes it so that the userId can be used later
                        View view = super.getView(position, convertView, parent);
                        String part = (String) getItem(position);
                        String[] parts = part.split("\\|");
                        String username = parts[0];
                        TextView text1 = view.findViewById(android.R.id.text1);
                        text1.setText(username);
                        return view;
                    }
                };

                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //Setting the new fragment which will display the user's profile page
                        String text = (String) adapterView.getItemAtPosition(i);
                        String[] parts = text.split("\\|");
                        String uid = parts[1];
                        Bundle bundle = new Bundle();
                        bundle.putString("userId", uid);
                        UserProfileFragment receiverFragment = new UserProfileFragment();
                        receiverFragment.setArguments(bundle);
                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.group_container, receiverFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        //Setting the search bar in the menu
        inflater.inflate(R.menu.main_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (usersList.contains(s)) {
                    adapter.getFilter().filter(s);
                } else {
                    // Search query not found in List View
                    Toast.makeText(InviteUsersFragment.this.getContext(), "Not found", Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
}