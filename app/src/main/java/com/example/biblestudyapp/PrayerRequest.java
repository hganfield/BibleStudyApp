package com.example.biblestudyapp;

import java.time.LocalDate;
import java.time.LocalTime;

public class PrayerRequest {

    private String text;

    private User requestor;

    private Group group;


    private LocalTime time;

    private LocalDate date;

    public PrayerRequest(String text, User requestor, Group group) {
        this.text = text;
        this.requestor = requestor;
        this.group = group;
        this.time = LocalTime.now();
        this.date = LocalDate.now();

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

    public LocalDate getDate() { return date;}

    /**
     *  when update the content of the prayer request, use this method to update
     *  the new date and time for the revised version of the object
     */
    public void updateDateTime() {
        date = LocalDate.now();
        time = LocalTime.now();
    }

    public LocalTime getTime() {return time;}

}
