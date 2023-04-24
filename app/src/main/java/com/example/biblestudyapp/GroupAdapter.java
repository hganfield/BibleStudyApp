package com.example.biblestudyapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {

    private List<Group> groupList;

    private OnItemClickListener listener;

    public GroupAdapter(List<Group> groupList,OnItemClickListener listener){
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
        Group group = groupList.get(position);
        holder.titleTextView.setText(group.getGroupName());
    }

    @Override
    public int getItemCount() {
        if(groupList == null){
            return 0;
        }
        return groupList.size();
    }


    public class GroupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView titleTextView;
        public TextView verseTextView;
        public TextView dateTextView;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.group_title);
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
