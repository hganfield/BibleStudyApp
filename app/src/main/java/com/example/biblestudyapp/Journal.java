package com.example.biblestudyapp;

import java.util.List;

public class Journal {

    private String title;

    private User author;

    private int created_date;

    //TODO:private BibleVerse verse;

    private String text;

    //TODO: add time/clock and verse to the constructor;
    public Journal(String title, User author ) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() {return title;}

    public String setTitle() {return title;}

    public User getAuthor() {return author;}

    public String getText() { return text;}

    public String setText() {return text;}





}
