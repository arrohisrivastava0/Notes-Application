package com.example.notesapplication.todo_list;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class TodoListDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "todo_list_database";
    private static final int DATABASE_VERSION= 1;
    public static final String TABLE_TODO_LIST = "todo_list";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TODO_HEADING = "todo_heading";
    public static final String COLUMN_TODO_ITEM="todo_list_item";
    public static final String COLUMN_TODO_NO_ITEM="todo_list_item_no";
    public static final String TABLE_TODO_LIST_ITEMS = "todo_list_items";
    public static final String COLUMN_LIST_ID = "list_id";
    public static final String COLUMN_IS_COMPLETED = "is_completed";


    private static final String TABLE_TODO_CREATE =
            "CREATE TABLE " + TABLE_TODO_LIST + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TODO_NO_ITEM+" TEXT,"+
                    COLUMN_TODO_HEADING+" TEXT);";
    private static final String TABLE_TODO_ITEMS_CREATE =
            "CREATE TABLE " + TABLE_TODO_LIST_ITEMS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_LIST_ID + " INTEGER, "
                    + COLUMN_TODO_ITEM+" TEXT, "+ COLUMN_IS_COMPLETED+ " INTEGER DEFAULT 0, "+
                    "FOREIGN KEY("+COLUMN_LIST_ID+") REFERENCES " + TABLE_TODO_LIST  + "("+COLUMN_ID+"))";

    public TodoListDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_TODO_CREATE);
        db.execSQL(TABLE_TODO_ITEMS_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_TODO_LIST);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_TODO_LIST_ITEMS);
        onCreate(db);
    }
}
