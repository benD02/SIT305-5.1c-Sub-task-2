package com.example.subtask2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlaylistActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerView;
    private PlaylistAdapter adapter;
    private int currentUserId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_playlist); // Consider renaming this layout file

        databaseHelper = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.playlistRecyclerView);

        currentUserId = getCurrentUserIdFromIntent();

        if (currentUserId != -1) {
            List<String> playlist = databaseHelper.getUserPlaylist(currentUserId);
            setupRecyclerView(playlist);
        }
    }

    private void setupRecyclerView(List<String> playlist) {
        adapter = new PlaylistAdapter(playlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private int getCurrentUserIdFromIntent() {
        // This method assumes you are passing the current user ID as an extra in the intent
        return getIntent().getIntExtra("currentUserId", -1);
    }
}
