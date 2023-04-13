package com.example.biblestudyapp;

import android.graphics.Color;

public class Highlight {

    /*
    The verse that the highlight will be on
     */
    private String verse;

    /*
    The color of the highlight
     */
    private int color;

    /*
    Id for the highlight
     */
    private String highlightId;

    public Highlight(){

    }
    public Highlight(String verse, int color,String highlightId){
        this.highlightId = highlightId;
        this.verse = verse;
        this.color = color;
    }

    public void setHighlightId(String Id){
        highlightId = Id;
    }

    public String getHighlightId(){return highlightId;}

    public void setVerse(String verseref){
        verse = verseref;
    }

    public void setColor(int col){
        color = col;
    }

    public String getVerse(){return verse;}

    public int getColor(){return color;}

}
