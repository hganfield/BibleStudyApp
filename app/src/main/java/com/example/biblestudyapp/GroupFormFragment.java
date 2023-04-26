package com.example.biblestudyapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupFormFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /*
    The GroupName textbox the user types in
     */
    private TextInputEditText groupName;

    /*
    The option of private or public the user selects
     */
    private Switch isPrivate;

    /*
    The password textbox the user types in
     */
    private TextInputEditText password;


    public GroupFormFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GroupFormFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GroupFormFragment newInstance(String param1, String param2) {
        GroupFormFragment fragment = new GroupFormFragment();
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group_form, container, false);

        //Initializing all of the variables
        groupName = view.findViewById(R.id.group_name);
        password = view.findViewById(R.id.password);
        isPrivate = view.findViewById(R.id.publicGroup);
        Button addMems = (Button) view.findViewById(R.id.addMembers);

        //Checks to see if the switch has been changed if it has it will have an effect on if the password is visible or not
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

        addMems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Retrieves Data from the textboxes
                String name = groupName.getText().toString();
                String passwordtxt = password.getText().toString();
                boolean pbool = isPrivate.isChecked();

                //Error checking
                if(name.equals("")){
                    Toast.makeText(getContext(), "Please Enter A Group Name",
                            Toast.LENGTH_SHORT).show();

                }else if(pbool && passwordtxt.equals("")) {

                    Toast.makeText(getContext(), "Please Enter A Password",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    Bundle bundle = new Bundle();
                    System.out.println(name);
                    bundle.putString("name",name);
                    bundle.putBoolean("private",pbool);
                    bundle.putString("password",passwordtxt);

                    //Open new fragment to invite users to the group
                    InviteUsersFragment receiverFragment = new InviteUsersFragment();
                    receiverFragment.setArguments(bundle);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.group_container, receiverFragment, null)
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        return view;
    }
}