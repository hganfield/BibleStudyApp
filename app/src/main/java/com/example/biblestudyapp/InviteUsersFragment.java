package com.example.biblestudyapp;

import android.content.Context;
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
        getActivity().setContentView(R.layout.fragment_invite_users);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    private ArrayList<String> usersList;
    private FirebaseAuth firebaseAuth;
    // List View object
    private ListView listView;

    // Define array adapter for ListView
    private ArrayAdapter<String> adapter;

    private Context context;

    private DatabaseReference reference;

    private DatabaseReference group_ref;

    private SharedViewModel sharedViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = this.getContext();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_invite_users, container, false);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.printList();

        listView = view.findViewById(R.id.listView);
        firebaseAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users");
        group_ref = FirebaseDatabase.getInstance().getReference("groups");
        getAllUsers();
        Button createGroup = (Button) view.findViewById(R.id.finish_group);
        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<User> groupList = sharedViewModel.getUserList().getValue();
                if(groupList == null ||groupList.isEmpty()){
                }
                else{
                    Bundle bundle = new Bundle();
                    String g_name = bundle.getString("name");
                    boolean privatebool = bundle.getBoolean("private");
                    String groupId = group_ref.push().getKey();
                    if(privatebool){
                        String password = bundle.getString("password");
                        Group group = new Group(groupList,g_name,groupId,privatebool,password);
                    }
                    Group group = new Group(groupList,g_name,groupId,privatebool);
                    group_ref.child(groupId).setValue(group);
                    for(User u : groupList){
                        u.addGroup(group);
                    }
                    getActivity().finish();

                }


            }
        });
        return view;
    }
    private void getAllUsers() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    User user = dataSnapshot1.getValue(User.class);
                    if(user.getUid() != null && !user.getUid().equals(firebaseAuth.getCurrentUser().getUid())) {
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
                        String text = (String) adapterView.getItemAtPosition(i);
                        String[] parts = text.split("\\|");
                        String uid = parts[1];
                        Bundle bundle = new Bundle();
                        bundle.putString("userId", uid);
                        UserProfileFragment receiverFragment = new UserProfileFragment();
                        receiverFragment.setArguments(bundle);
                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.InviteUsers, receiverFragment);
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