package com.example.biblestudyapp;

import java.util.List;

public class Group {
    private List<User> members;

    public Group(List<User> list){
        this.members = list;
    }

    public List<User> getMembers() {
        return members;
    }

    public void addMember(User user){
        this.members.add(user);
    }
}
