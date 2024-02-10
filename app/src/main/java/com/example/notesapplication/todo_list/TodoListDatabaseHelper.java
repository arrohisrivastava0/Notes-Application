package com.example.notesapplication.todo_list;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class TodoListDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "todo_list_database";
    private static final int DATABASE_VERSION= 1;
    public static final String TABLE_TODO = "todo_list";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TODO_ITEM="todo_list_item";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_TODO + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TODO_ITEM+" TEXT);";

    public TodoListDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_TODO);
        onCreate(db);
    }
}
