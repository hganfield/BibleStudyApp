package com.example.biblestudyapp;

import java.util.List;

public class Group {
    private List<User> members;

    private String name;

    private String groupId;

    private boolean isPrivate;

    private String password;

    public Group(){}
    public Group(List<User> list, String name,String groupId, boolean isPrivate){
        this.groupId = groupId;
        this.members = list;
        this.name = name;
        this.isPrivate = isPrivate;
        password = "";
    }

    public Group(List<User> list, String name,String groupId, boolean isPrivate,String password){
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

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> listmem){members = listmem;}

    public void setGroupName(String name1){name =name1;}


    public String getGroupName(){ return name;}

    public void addMember(User user){
        this.members.add(user);
    }

    public void removeMember(User user){this.members.remove(user);}
}
