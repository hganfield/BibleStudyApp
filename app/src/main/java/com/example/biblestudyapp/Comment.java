package com.example.biblestudyapp;

import java.util.List;

public class Comment {

        private String text;

        private User author;

        private int created_date;

        //TODO: add time/clock to the constructor;
        public Comment (String text, User author) {
            this.text = text;
            this.author = author;
        }

        public String getText() {return text;}

        public String setText() {return text;}

        public User getAuthor() {return author;}
}
