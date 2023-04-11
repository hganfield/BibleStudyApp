package com.example.biblestudyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class Books extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        WebView booksview = (WebView) findViewById(R.id.booksview);
        booksview.getSettings().setJavaScriptEnabled(true);
        booksview.loadUrl("file:///android_asset/books.html");
    }
}