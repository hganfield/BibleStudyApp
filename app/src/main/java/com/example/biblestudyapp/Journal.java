package com.example.biblestudyapp;

import java.util.Date;
import java.util.List;

public class Journal {

    private String title;

    private String author;

    private Date created_date;

    //TODO:private BibleVerse verse;

    private String text;

    private String ref;

    //TODO: add time/clock and verse to the constructor;
    public Journal(String title, String author,String ref, Date created_date) {
        this.created_date = created_date;
        this.title = title;
        this.author = author;
        this.ref = ref;
    }

    public String getRef(){return ref;}

    public void setRef(String reference){ref = reference;}
    public Date getDate(){return created_date;}

    public void setDate(Date time){ created_date = time;}
    public String getTitle() {return title;}

    public void setTitle(String titleText) {title = titleText;}

    public String getAuthor() {return author;}

    public String getJournalText() { return text;}

    public void setJournalText(String jText) {text = jText;}





}
