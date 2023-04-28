package com.example.biblestudyapp;

import java.time.LocalDate;
import java.time.LocalTime;

public class PrayerRequest {

    private String title;
    private String text;

    private String user;

    private String group;


    private LocalTime time;

    private LocalDate date;

    public PrayerRequest(String title, String text, String requestor, String group) {
        this.title = title;
        this.text = text;
        this.user = requestor;
        this.group = group;
        this.time = LocalTime.now();
        this.date = LocalDate.now();

    }

    public String getTitle() { return title;}

    public void setTitle(String title) { this.title = title;}
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
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
