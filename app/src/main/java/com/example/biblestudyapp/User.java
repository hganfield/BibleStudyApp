package com.example.biblestudyapp;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String uid;
    private String username;
    private String email;

    private String phoneNumber;
    private List<Group> groups;

    private DatabaseReference mDatabase;

    public User(){

    }
    public User(String uid, String username, String email, String phoneNumber){
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        groups = new ArrayList<Group>();
    }
    public User(String uid, String username, String email, String phoneNumber, ArrayList<Group> groups){
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.groups = groups;
    }

    public String getPhoneNumber(){
        return phoneNumber;
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
