package com.example.biblestudyapp;

import java.time.Clock;

public class PrayerRequest {

    private String text;

    private User requestor;

    private Group group;

    // TODO: time it was made
   //  private final Clock dateTime;

    public PrayerRequest(String text, User requestor, Group group) {
        this.text = text;
        this.requestor = requestor;
        this.group = group;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getRequestor() {
        return requestor;
    }

    public void setRequestor(User requestor) {
        this.requestor = requestor;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

}
