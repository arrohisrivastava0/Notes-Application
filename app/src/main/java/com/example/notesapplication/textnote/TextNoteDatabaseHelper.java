package com.example.notesapplication.textnote;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class TextNoteDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "text_note_database";
    private static final int DATABASE_VERSION= 1;

    public static final String TABLE_NOTES = "text_notes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NOTE_HEADING = "text_note_heading";
    public static final String COLUMN_NOTE_TEXT="text_note_text";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NOTES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOTE_HEADING + " TEXT, " + COLUMN_NOTE_TEXT+" TEXT);";

    public TextNoteDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NOTES);
        onCreate(db);
    }
}
