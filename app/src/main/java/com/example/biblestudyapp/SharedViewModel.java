package com.example.biblestudyapp;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class SharedViewModel extends ViewModel {

    private MutableLiveData<List<User>> listusers = new MutableLiveData<>();

    public MutableLiveData<List<User>> getUserList(){
        return listusers;
    }

    public void setUserList(List<User> userList){
        this.listusers.postValue(userList);
    }

    public boolean containsUser(User user){
        if(this.listusers.getValue() == null || this.listusers.getValue().isEmpty()){
            return false;
        }
        return this.listusers.getValue().contains(user);
    }


    public void printList(){
        if(this.listusers.getValue() == null){
            return;
        }
        for(User user : this.listusers.getValue()){
            System.out.println(user.getUsername());
        }
    }
}
