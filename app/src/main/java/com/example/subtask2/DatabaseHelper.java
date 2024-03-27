package com.example.subtask2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FULL_NAME = "full_name";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    private static final String TABLE_PLAYLIST = "playlist";
    private static final String COLUMN_URL = "url";
    private static final String COLUMN_USER_ID = "user_id";

    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_FULL_NAME + " TEXT,"
            + COLUMN_USERNAME + " TEXT,"
            + COLUMN_PASSWORD + " TEXT" + ")";

    private static final String CREATE_TABLE_PLAYLIST = "CREATE TABLE " + TABLE_PLAYLIST + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_ID + " INTEGER,"
            + COLUMN_URL + " TEXT,"
            + "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_ID + ")" + ")";


    public DatabaseHelper(FragmentActivity context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_PLAYLIST);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public void addUser(String fullName, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FULL_NAME, fullName);
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);

        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COLUMN_ID},
                COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{username, password}, null, null, null);

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        return cursorCount > 0;
    }


    @SuppressLint("Range")
    public int getUserIdByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_ID},
                COLUMN_USERNAME + "=?", new String[]{username}, null, null, null);

        int userId = -1; // Default to -1 to indicate not found
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
        }
        cursor.close();
        db.close();
        return userId;
    }


    public void deletePlaylistItem(int userId, String url) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLAYLIST, COLUMN_USER_ID + "=? AND " + COLUMN_URL + "=?", new String[]{String.valueOf(userId), url});
        db.close();
    }



    public void addUrlToPlaylist(int userId, String url) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_URL, url);

        db.insert(TABLE_PLAYLIST, null, values);
        db.close();
    }

    // Method to fetch all URLs from the playlist for a specific user
    @SuppressLint("Range")
    public List<String> getUserPlaylist(int userId) {
        List<String> playlist = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_URL};
        String selection = COLUMN_USER_ID + "=?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.query(TABLE_PLAYLIST, columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                playlist.add(cursor.getString(cursor.getColumnIndex(COLUMN_URL)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return playlist;
    }
}
