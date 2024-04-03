package com.example.subtask2;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlaylistActivity extends AppCompatActivity implements PlaylistAdapter.ItemDeleteListener {

    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerView;
    private PlaylistAdapter adapter;
    private int currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_playlist);

        databaseHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.playlistRecyclerView);
        String currentUsername = getCurrentUsername();
        currentUserId = databaseHelper.getUserIdByUsername(currentUsername);

        if (currentUserId != -1) {
            refreshPlaylist();
        }
    }

    private String getCurrentUsername() {
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("Username", null);
    }

    private void refreshPlaylist() {
        List<String> playlist = databaseHelper.getUserPlaylist(currentUserId);
        adapter = new PlaylistAdapter(playlist, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onItemDelete(int position) {
        String urlToDelete = adapter.playlist.get(position);
        databaseHelper.deletePlaylistItem(currentUserId, urlToDelete);
        refreshPlaylist();
    }
}