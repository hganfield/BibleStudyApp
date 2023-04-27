package com.example.biblestudyapp;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String uid;
    private String username;
    private String email;

    private String profilePicture;

    private List<Journal> journalList;
    private String phoneNumber;
    private List<String> groups;

   // private ImageView profile_picture;
    private DatabaseReference mDatabase;

    public User(){

    }

    public User(String uid, String username, String email, String phoneNumber, String profilePicture) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.profilePicture = profilePicture;
        journalList = new ArrayList<Journal>();
        groups = new ArrayList<String>();
    }
    public User(String uid, String username, String email, String phoneNumber){
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        journalList = new ArrayList<Journal>();
        groups = new ArrayList<String>();
        profilePicture = "";
    }
    public User(String uid, String username, String email, String phoneNumber, ArrayList<String> groups){
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        journalList = new ArrayList<Journal>();
        this.groups = groups;
        profilePicture = "";
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }
public void setPhoneNumber(String number) {this.phoneNumber = number;}
    public void setUsername(String name) {this.username = name;}
    public List<Journal> getJournals() {
        return journalList;
    }
    public String getUid() {
        return uid;
    }

    public List<String> getGroups() {
        return groups;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getUsername() {
        return username;
    }

    public void addGroup(String group){
        if(this.groups == null){
            this.groups = new ArrayList<String>();
        }
        this.groups.add(group);

    }

    public void updateDB(){
        try {
            FirebaseDatabase.getInstance().getReference("users").child(this.getUid()).setValue(this);
        } catch (Exception e) {
            Log.e("Update DB error", e.getMessage());
        }
    }
}
