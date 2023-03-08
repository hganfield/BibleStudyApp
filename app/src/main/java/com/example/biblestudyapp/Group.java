package com.example.biblestudyapp;

import java.util.List;

public class Group {
    private List<User> members;

    private String name;

    public Group(){}
    public Group(List<User> list, String name){
        this.members = list;
        this.name = name;
    }
    public List<User> getMembers() {
        return members;
    }

    public String getGroupName(){ return name;}

    public void addMember(User user){
        this.members.add(user);
    }
}
