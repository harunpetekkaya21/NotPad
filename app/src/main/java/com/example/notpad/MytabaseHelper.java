package com.example.notpad;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MytabaseHelper extends SQLiteOpenHelper {

    public MytabaseHelper(@Nullable Context context) {
        super(context, "notepadDB.sqlite", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        /*    private int noteId;
    private String noteTitle;
    private String noteDescription;
    private String noteCategory;
    private byte[] noteImage;
        * */

        sqLiteDatabase.execSQL("CREATE TABLE notepad (note_id INTEGER PRIMARY KEY AUTOINCREMENT,note_title TEXT, note_description TEXT, note_category TEXT, note_image BLOB)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS notepad");
        onCreate(sqLiteDatabase);
    }
}

