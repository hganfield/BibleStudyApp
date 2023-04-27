package com.example.biblestudyapp;

import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class SearchUtility {
    public static <T> void getAllItems(DatabaseReference reference, Class<T> itemClass, List<T> itemsList, ArrayAdapter<T> adapter) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemsList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    T item = dataSnapshot1.getValue(itemClass);
                    itemsList.add(item);
                }
                if(itemsList.isEmpty()){
                    Toast.makeText(adapter.getContext(), "Empty", Toast.LENGTH_LONG).show();
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

}
