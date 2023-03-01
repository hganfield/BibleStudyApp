package com.example.biblestudyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;


public class BiblePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bible_page);

        WebView bible = (WebView) findViewById(R.id.Bible);
        bible.loadUrl("file:///android_asset/bible.html");
    }
}