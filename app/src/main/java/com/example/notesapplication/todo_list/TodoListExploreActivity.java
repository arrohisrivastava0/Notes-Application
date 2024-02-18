package com.example.notesapplication.todo_list;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapplication.R;
import com.example.notesapplication.textnote.TextNoteActivity;
import com.example.notesapplication.textnote.TextNoteData;
import com.example.notesapplication.textnote.TextNoteDatabaseHelper;
import com.example.notesapplication.textnote.TextNoteExploreActivity;
import com.example.notesapplication.textnote.TextNoteExploreRVAdapter;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TodoListExploreActivity extends AppCompatActivity {
    private boolean isInSelectionMode = false;
    private ImageButton menu;
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private TodoListDatabaseHelper todoListDatabaseHelper;
    private TodoListExploreRVAdapter todoListExploreRVAdapter;
    private SearchView searchView;
    private BottomAppBar bottomAppBar;
    private Button materialButtonSelectAll, materialButtonDelete;
    private TextView textViewNothing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_explore);
        menu=findViewById(R.id.todoExploreMenu);
        floatingActionButton=findViewById(R.id.TodofloatAdd);
        recyclerView=findViewById(R.id.RVTodoListExplore);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        todoListDatabaseHelper=new TodoListDatabaseHelper(this);
        searchView=findViewById(R.id.todoexploreSV);
        List<TodoListExploreData> todoListExploreData=getAllTodos();
        todoListExploreRVAdapter=new TodoListExploreRVAdapter(todoListExploreData, this);
        recyclerView.setAdapter(todoListExploreRVAdapter);
        bottomAppBar=findViewById(R.id.todobottomAppBar);
        materialButtonSelectAll=findViewById(R.id.TodobtnSelectAll);
        materialButtonDelete=findViewById(R.id.TodobtnDelete);
        textViewNothing=findViewById(R.id.TodoListExpNothingTV);
        if (!getAllTodos().isEmpty())
            textViewNothing.setVisibility(View.GONE);
        setCreateNewClickListener();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (getAllTodos().isEmpty())
            textViewNothing.setVisibility(View.VISIBLE);
        else textViewNothing.setVisibility(View.GONE);
        if (!searchView.getQuery().toString().isEmpty()) {
            filter(searchView.getQuery().toString());
        } else {
            // Otherwise, refresh with all notes
            todoListExploreRVAdapter.setData(getAllTodos());
            todoListExploreRVAdapter.notifyDataSetChanged();
        }

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        todoListDatabaseHelper.close();
    }

    private void setCreateNewClickListener(){
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TodoListExploreActivity.this, TodoListActivity.class);
                intent.putExtra("isNewNote", true);
                startActivity(intent);
            }
        });
    }

    private void filter(String query) {
        List<TodoListExploreData> filteredList = new ArrayList<>();
        for (TodoListExploreData todo : getAllTodos()) {
            if (todo.getHeading().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(todo);
            }
        }
        todoListExploreRVAdapter.setData(filteredList);
    }

    private List<TodoListExploreData> getAllTodos() {
        List<TodoListExploreData> todoListExploreData=new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=todoListDatabaseHelper.getReadableDatabase();
        String[] projection = {
                TodoListDatabaseHelper.COLUMN_ID,
                TodoListDatabaseHelper.COLUMN_TODO_HEADING,
                TodoListDatabaseHelper.COLUMN_TODO_NO_ITEM
        };

        Cursor cursor=sqLiteDatabase.query(
                TodoListDatabaseHelper.TABLE_TODO_LIST,
                projection,
                null,
                null,
                null,
                null,
                TextNoteDatabaseHelper.COLUMN_ID + " DESC"
        );
        if (cursor != null&&cursor.moveToFirst()){
            do{
                @SuppressLint("Range") long id=cursor.getLong(cursor.getColumnIndex(TodoListDatabaseHelper.COLUMN_ID));
                @SuppressLint("Range") String heading=cursor.getString(cursor.getColumnIndex(TodoListDatabaseHelper.COLUMN_TODO_HEADING));
                @SuppressLint("Range") String noItemms=cursor.getString(cursor.getColumnIndex(TodoListDatabaseHelper.COLUMN_TODO_NO_ITEM));
                todoListExploreData.add(new TodoListExploreData(id,heading, noItemms));
            }while (cursor.moveToNext());
        }

        if (cursor != null){
            cursor.close();
        }
        sqLiteDatabase.close();
        return todoListExploreData;
    }

    public void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.todo_list_explore_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            // Handle menu item clicks here
            if (item.getItemId()==R.id.action_select && !getAllTodos().isEmpty()){
                isInSelectionMode=!isInSelectionMode;
                updateRecyclerView();
                bottomAppBar.setVisibility(View.VISIBLE);
                setClickListenersBottomAppBar();
                return true;
            }
            Toast.makeText(this,"No items", Toast.LENGTH_SHORT).show();
            return false;
        });

        popupMenu.show();
    }

    private void setClickListenersBottomAppBar(){
        materialButtonSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAllItems();
            }
        });
        materialButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSelectedItems();
            }
        });

    }
    private void deleteSelectedItems() {
        List<Long> selectedItems = new ArrayList<>();
        for (TodoListExploreData todo : todoListExploreRVAdapter.getTodoListData()) {

            if (todo.isSelected()) {
                selectedItems.add(todo.getId());
            }
        }
        showConfirmationDialog(selectedItems);
    }

    private void showConfirmationDialog(List<Long> selectedItems) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Permanently delete "+selectedItems.size()+" notes?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteFromDb(selectedItems);
                todoListExploreRVAdapter.setData(getAllTodos());
                isInSelectionMode=!isInSelectionMode;
                updateRecyclerView();
                bottomAppBar.setVisibility(View.GONE);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // If the user cancels, dismiss the dialog and do nothing
                todoListExploreRVAdapter.setData(getAllTodos());
                isInSelectionMode=!isInSelectionMode;
                updateRecyclerView();
                bottomAppBar.setVisibility(View.GONE);
                dialog.dismiss();
            }
        });
        builder.show();

    }

    private void deleteFromDb(List<Long> items){
        SQLiteDatabase sqLiteDatabase=todoListDatabaseHelper.getWritableDatabase();
        try {
            for(long todoId:items){
                sqLiteDatabase.delete(TodoListDatabaseHelper.TABLE_TODO_LIST, TextNoteDatabaseHelper.COLUMN_ID+"=?",
                        new String[]{String.valueOf(todoId)});
            }
        }finally {
            sqLiteDatabase.close();
        }
    }
    private void selectAllItems() {
        for (TodoListExploreData todo : todoListExploreRVAdapter.getTodoListData()) {
            todo.setSelected(true);
        }
        updateRecyclerView();
    }

    private void updateRecyclerView() {
        todoListExploreRVAdapter.setInSelectionMode(isInSelectionMode);
        todoListExploreRVAdapter.notifyDataSetChanged();
        if (getAllTodos().isEmpty())
            textViewNothing.setVisibility(View.VISIBLE);
        else textViewNothing.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (shouldHandleBackNavigation()) {
            todoListExploreRVAdapter.setData(getAllTodos());
            isInSelectionMode=!isInSelectionMode;
            updateRecyclerView();
            bottomAppBar.setVisibility(View.GONE);
        } else {

            super.onBackPressed();
        }
    }

    private boolean shouldHandleBackNavigation() {
        if (isInSelectionMode)
            return true;
        return false;
    }
}
