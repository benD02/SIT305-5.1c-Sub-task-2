package com.example.subtask2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class mainMenuActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private int currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main_menu_activity); // You should rename this layout to reflect that it's now used by an Activity

        databaseHelper = new DatabaseHelper(this);
        EditText urlEditText = findViewById(R.id.url);
        Button addButton = findViewById(R.id.btn_add);
        Button playlistButton = findViewById(R.id.btn_playlist);

        String currentUsername = getCurrentUsername();
        currentUserId = databaseHelper.getUserIdByUsername(currentUsername);

        addButton.setOnClickListener(v -> {
            String url = urlEditText.getText().toString().trim();
            if (!url.isEmpty()) {
                databaseHelper.addUrlToPlaylist(currentUserId, url);
                Toast.makeText(mainMenuActivity.this, "URL added to playlist", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mainMenuActivity.this, "Please enter a URL", Toast.LENGTH_SHORT).show();
            }
        });

        playlistButton.setOnClickListener(v -> {
            // Prepare an intent to start PlaylistActivity
            Intent intent = new Intent(mainMenuActivity.this, PlaylistActivity.class);

            // Pass the current user ID to PlaylistActivity
            intent.putExtra("currentUserId", currentUserId);

            // Start PlaylistActivity
            startActivity(intent);
        });


    }



    private String getCurrentUsername() {
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("Username", null); // Return null if "Username" doesn't exist
    }
}
