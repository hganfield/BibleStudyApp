package com.example.biblestudyapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Use the {@link SearchGroupsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchGroupsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchGroupsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchGroupsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchGroupsFragment newInstance(String param1, String param2) {
        SearchGroupsFragment fragment = new SearchGroupsFragment();
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

    private RecyclerView recyclerView;
    private GroupAdapter groupAdapter;
    private List<String> groupList;
    private DatabaseReference groupDatabase;
    private FirebaseUser user;
    private SearchView searchView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_groups, container, false);

        searchView = view.findViewById(R.id.searchView);
        recyclerView = view.findViewById(R.id.search_results_list);

        user = FirebaseAuth.getInstance().getCurrentUser();
        groupDatabase = FirebaseDatabase.getInstance().getReference("groups");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //When the user submits a query, clear the current list of groups and perform the search
                //groupList.clear();
                searchGroups(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //When the user changes the query text, do nothing
                return false;
            }
        });

        return view;
    }


            /*
            Searches for groups based on the given query
             */
            private void searchGroups(String query) {
                groupDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        groupList = new ArrayList<>();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            System.out.println("Hey");
                            Group group = dataSnapshot1.getValue(Group.class);
                            System.out.println(group.getGroupName());
                            if (group.getGroupName().toLowerCase().contains(query.toLowerCase())) {
                                groupList.add(group.getGroupId());
                            }
                        }
                        if(groupList == null || groupList.isEmpty()){
                            System.out.println("Empty");
                        }
                        else{
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            groupAdapter = new GroupAdapter(groupList, getContext(),true);
                            recyclerView.setAdapter(groupAdapter);
                            groupAdapter.notifyDataSetChanged();
                        }

                    }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
            }
}