package com.example.biblestudyapp;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String uid;
    private String username;
    private String email;

    private List<Journal> journalList;
    private String phoneNumber;
    private List<Group> groups;

    private ImageView profile_picture;
    private DatabaseReference mDatabase;

    public User(){

    }
    public User(String uid, String username, String email, String phoneNumber){
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        journalList = new ArrayList<Journal>();
        groups = new ArrayList<Group>();
        profile_picture = null;
    }
    public User(String uid, String username, String email, String phoneNumber, ArrayList<Group> groups){
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        journalList = new ArrayList<Journal>();
        this.groups = groups;
        profile_picture = null;
    }

    public Drawable getProfile_picture() { return profile_picture.getDrawable(); }
    public String getPhoneNumber(){
        return phoneNumber;
    }

    public List<Journal> getJournals() {
        return journalList;
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
