package com.example.biblestudyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;


public class BiblePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bible_page);

        getIntent().addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        WebView bible = (WebView) findViewById(R.id.Bible);
        bible.getSettings().setJavaScriptEnabled(true);
        bible.getSettings().setUseWideViewPort(true);
        bible.loadUrl("file:///android_asset/index.html");
    }
}