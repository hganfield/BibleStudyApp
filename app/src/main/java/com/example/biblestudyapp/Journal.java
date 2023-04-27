package com.example.biblestudyapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Journal extends AppCompatActivity {

    private String title;

    private String author;

    private Date created_date;

    //TODO:private BibleVerse verse;

    private String text;

    private String verse;

    //TODO: add time/clock and verse to the constructor;
    public Journal(String title, String author,String verse,Date created_date) {
        this.created_date = created_date;
        this.title = title;
        this.author = author;
        this.verse = verse;
    }

    public String getVerse(){return verse;}

    public void setVerse(String reference){verse = reference;}
    public Date getDate(){return created_date;}

    public void setDate(Date time){ created_date = time;}
    public String getJournalTitle() {return title;}

    public void setTitle(String titleText) {title = titleText;}

    public String getAuthor() {return author;}

    public void setAuthor(String id){author = id;}

    public String getJournalText() { return text;}

    public void setJournalText(String jText) {text = jText;}

    ArrayList<String> journal = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_journal);

        ListView listView = (ListView)findViewById(R.id.listView);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, journal);

    }
}
