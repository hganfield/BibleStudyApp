package com.example.biblestudyapp;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String uid;
    private String username;
    private String email;
    private List<Group> groups;

    private DatabaseReference mDatabase;

    public User(){

    }
    public User(String uid, String username, String email){
        this.uid = uid;
        this.username = username;
        this.email = email;
        groups = new ArrayList<Group>();
    }
    public User(String uid, String username, String email, ArrayList<Group> groups){
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.groups = groups;
    }

    public String getUid() {
        return uid;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }
    public void newGroup(Group group){
        if(this.groups == null){
            this.groups = new ArrayList<Group>();
        }
        this.groups.add(group);

    }
}
