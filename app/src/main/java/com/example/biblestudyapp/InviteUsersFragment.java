package com.example.biblestudyapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.ActionProvider;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
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


    ArrayList<String> usersList;
    FirebaseAuth firebaseAuth;
    // List View object
    ListView listView;

    // Define array adapter for ListView
    ArrayAdapter<String> adapter;

    Context context;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = this.getContext();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_invite_users, container, false);
        listView = view.findViewById(R.id.listView);
        firebaseAuth = FirebaseAuth.getInstance();
        getAllUsers();
        //usersList.add("Hello");
        //if(usersList.isEmpty()){
        //    Toast.makeText(InviteUsersFragment.this.getContext(), "Empty", Toast.LENGTH_LONG).show();

       //}
        return view;
    }
    private void getAllUsers() {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    User user = dataSnapshot1.getValue(User.class);
                    if(user.getUid() != null && !user.getUid().equals(firebaseAuth.getCurrentUser().getUid())) {
                        //user.getUsername();
                        //Toast.makeText(InviteUsersFragment.this.getContext(), user.getUsername(), Toast.LENGTH_LONG).show();
                        usersList.add(user.getUsername());
                    }
                }
                if(usersList.isEmpty()){
                   Toast.makeText(InviteUsersFragment.this.getContext(), "Empty", Toast.LENGTH_LONG).show();

                }
                adapter = new ArrayAdapter<>(context,android.R.layout.simple_list_item_1,usersList);
                listView.setAdapter(adapter);
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