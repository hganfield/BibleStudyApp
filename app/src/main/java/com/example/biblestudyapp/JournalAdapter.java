package com.example.biblestudyapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.JournalViewHolder> {

        private List<Journal> mJournalList;

        public JournalAdapter(List<Journal> journalList) {
            mJournalList = journalList;
        }

        @NonNull
        @Override
        public JournalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.group_view, parent, false);
            return new JournalViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull JournalViewHolder holder, int position) {
            Journal journal = mJournalList.get(position);
            holder.titleTextView.setText(journal.getTitle());
            //holder.verseTextView.setText(journal.getVerseReference());
            //holder.dateTextView.setText(journal.getDateCreated().toString());
        }

        @Override
        public int getItemCount() {
            return mJournalList.size();
        }

        public static class JournalViewHolder extends RecyclerView.ViewHolder {
            public TextView titleTextView;
            public TextView verseTextView;
            public TextView dateTextView;

            public JournalViewHolder(@NonNull View itemView) {
                super(itemView);
                //titleTextView = itemView.findViewById(R.id.journal_title);
                //verseTextView = itemView.findViewById(R.id.verseTextView);
                //dateTextView = itemView.findViewById(R.id.dateTextView);
            }
        }

}
