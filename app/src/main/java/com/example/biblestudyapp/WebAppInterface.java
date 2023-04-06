package com.example.biblestudyapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class WebAppInterface {
    Context mContext;

    public WebAppInterface(Context c){
        mContext = c;
    }

    @JavascriptInterface
    public void showBible(String passage){
        //TextView myTextView = (TextView) ((Activity)mContext).findViewById(R.id.textView3);
       // myTextView.setText(myTextView.getText()+passage);

    }
}
