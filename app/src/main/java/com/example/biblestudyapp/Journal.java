package com.example.biblestudyapp;

import java.util.List;

public class Journal {

    private String title;

    private String author;

    private int created_date;

    //private BibleVerse verse;

    private String text;

    public Journal(String title, String author ) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() {return title;}

    public String getAuthor() {return author;}

    public String getText() { return text;}




}
