package com.example.biblestudyapp;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
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
                //groupList.add(groupId);


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

        ViewPager2 viewPager = view.findViewById(R.id.viewPager);
        GroupPagerAdapter g_adapter = new GroupPagerAdapter(this.getActivity());
        viewPager.setAdapter(g_adapter);

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    // Set the text for each tab
                    switch (position) {
                        case 0:
                            tab.setText("My Groups");
                            break;
                        case 1:
                            tab.setText("Search Groups");
                            break;
                        default:
                            tab.setText("Tab " + (position + 1));
                            break;
                    }
                }).attach();

        //Initialize all the variables that will be used in this fragment
        Button create = (Button) view.findViewById(R.id.Cbutton1);


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


    public class GroupPagerAdapter extends FragmentStateAdapter {
        private static final int NUM_PAGES = 2;

        public GroupPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new GroupFragment.MyGroupsFragment();
                case 1:
                    return new SearchGroupsFragment();
                default:
                    throw new IllegalStateException("Invalid position: " + position);
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }

    public static class MyGroupsFragment extends Fragment {

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
        The Adapter that allows the group name to be shown on the list as well as any other useful objects
         */
        private GroupAdapter groupAdapter;

        private RecyclerView recyclerView;

        @Override
        public void onResume() {
            super.onResume();
            refreshGroupList();
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_my_groups, container, false);
            recyclerView = view.findViewById(R.id.my_groups_list);
            user = FirebaseAuth.getInstance().getCurrentUser();
            groupDatabase = FirebaseDatabase.getInstance().getReference("users");
            groupList = new ArrayList<String>();
            refreshGroupList();


            return view;
        }

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
                        groupAdapter = new GroupAdapter(groupList,getContext(),false);
                        recyclerView.setAdapter(groupAdapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }




}