package com.example.biblestudyapp;

import android.util.Log;
import android.widget.ImageView;

import com.example.biblestudyapp.Journal.Journal;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String uid;
    private String username;
    private String email;

    private List<Journal> journalList;
    private String phoneNumber;
    private List<String> groups;

    private ImageView profile_picture;

    public User(){

    }
    public User(String uid, String username, String email, String phoneNumber){
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        journalList = new ArrayList<Journal>();
        groups = new ArrayList<String>();
        profile_picture = null;
    }
    public User(String uid, String username, String email, String phoneNumber, ArrayList<String> groups){
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        journalList = new ArrayList<Journal>();
        this.groups = groups;
        profile_picture = null;
    }


    public void setProfile_picture(ImageView image){this.profile_picture = image;}
    //public Drawable getProfile_picture() { return profile_picture.getDrawable(); }
    public String getPhoneNumber(){
        return phoneNumber;
    }

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
        //FirebaseDatabase.getInstance().getReference("users").child(this.getUid()).setValue(this);
    }

    @Override
    public boolean equals(Object o){
        if(o == null){
            return false;
        }
        if(o.getClass() != this.getClass()){
            return false;
        }
        final User other = (User) o;
        if(other.getUid() != this.getUid()){
            return false;
        }
        return true;
    }

}
