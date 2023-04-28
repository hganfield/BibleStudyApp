package com.example.biblestudyapp;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Group {
    private List<String> members;

    private String name;

    private String groupId;

    private boolean isPrivate;

    private String password;

    public Group(){}
    public Group(List<String> list, String name,String groupId, boolean isPrivate){
        this.groupId = groupId;
        this.members = list;
        this.name = name;
        this.isPrivate = isPrivate;
        this.password = "";
    }

    public Group(List<String> list, String name,String groupId, boolean isPrivate,String password){
        this.groupId = groupId;
        this.members = list;
        this.name = name;
        this.isPrivate = isPrivate;
        this.password = password;
    }

    public boolean getIsPrivate(){return isPrivate;}

    public void setPrivate(boolean value){this.isPrivate = value;}

    public String getPassword(){return password;}

    public void setPassword(String pass){this.password = pass;}

    public String getGroupId(){return groupId;}

    public void setGroupId(String id){groupId = id;}

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> listmem){members = listmem;}

    public void setGroupName(String name1){name =name1;}


    public String getGroupName(){ return name;}

    public void addMember(String user){
        this.members.add(user);
    }

    public void removeMember(String user){this.members.remove(user);}

    public static void getGroup(String groupId, final OnGroupRetrievedListener listener) {
        DatabaseReference groupRef = FirebaseDatabase.getInstance().getReference("groups").child(groupId);
        groupRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Group group = snapshot.getValue(Group.class);
                    listener.onGroupRetrieved(group);
                } else {
                    listener.onGroupRetrieved(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onGroupRetrieved(null);
            }
        });
    }

    public interface OnGroupRetrievedListener {
        void onGroupRetrieved(Group group);
    }

}
