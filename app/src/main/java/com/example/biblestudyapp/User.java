package com.example.biblestudyapp;

import android.widget.ImageView;

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

    public List<Group> getGroups() {
        return groups;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }
    public void addGroup(Group group){
        if(this.groups == null){
            this.groups = new ArrayList<Group>();
        }
        this.groups.add(group);

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
