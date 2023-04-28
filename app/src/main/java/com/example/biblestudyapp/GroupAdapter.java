package com.example.biblestudyapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {

    private List<String> groupList;

    private boolean allGroups;

    private OnItemClickListener listener;

    private Context context;

    public GroupAdapter(List<String> groupList,Context context,boolean allGroups){
        this.groupList = groupList;
        this.context = context;
        this.allGroups = allGroups;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public GroupAdapter.GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_view, parent, false);
        return new GroupAdapter.GroupViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupAdapter.GroupViewHolder holder, int position) {
        String groupId = groupList.get(position);
        Group.getGroup(groupId, new Group.OnGroupRetrievedListener() {
            @Override
            public void onGroupRetrieved(Group group) {
                if (group != null) {
                    holder.nameTextView.setText(group.getGroupName());
                    holder.nameTextView.setTag(groupId);
                } else {
                    holder.nameTextView.setText("Group not found");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if(groupList == null){
            return 0;
        }
        return groupList.size();
    }


    public class GroupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nameTextView;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.group_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(allGroups){
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Will You Like To Join?");

                builder.setPositiveButton("Join Group", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String groupId = nameTextView.getTag().toString();
                        System.out.println(groupId);
                        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        FirebaseDatabase.getInstance().getReference("groups").child(groupId).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Group g = snapshot.getValue(Group.class);
                                g.addMember(user);
                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        FirebaseDatabase.getInstance().getReference("users").child(user).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                User u = snapshot.getValue(User.class);
                                u.addGroup(groupId);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });

                builder.setNegativeButton("Nope", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.out.println("Clicking");
                    }
                });

                // Display the dialog box
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else{
                String groupId = nameTextView.getTag().toString();
                Intent intent = new Intent(context,GroupHomePage.class);
                intent.putExtra("group_page_id", groupId);
                context.startActivity(intent);
            }

        }
    }
}
