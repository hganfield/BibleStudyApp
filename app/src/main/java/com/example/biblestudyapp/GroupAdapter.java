package com.example.biblestudyapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {

    private List<String> groupList;

    private OnItemClickListener listener;

    public GroupAdapter(List<String> groupList,OnItemClickListener listener){
        this.groupList = groupList;
        this.listener = listener;
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
        public TextView verseTextView;
        public TextView dateTextView;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.group_title);
            itemView.setOnClickListener(this);
            //verseTextView = itemView.findViewById(R.id.verseTextView);
            //dateTextView = itemView.findViewById(R.id.dateTextView);
        }

        @Override
        public void onClick(View view) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
        }
    }
}
