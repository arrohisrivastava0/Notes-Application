package com.example.notesapplication.todo_list;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.notesapplication.R;
import com.example.notesapplication.textnote.TextNoteDatabaseHelper;

public class TodoListActivity extends AppCompatActivity {
    ImageButton saveTodoList;
    private TodoListDatabaseHelper todoListDatabaseHelper;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        todoListDatabaseHelper=new TodoListDatabaseHelper(this);
        saveTodoList=findViewById(R.id.saveTodoList);
    }
}
