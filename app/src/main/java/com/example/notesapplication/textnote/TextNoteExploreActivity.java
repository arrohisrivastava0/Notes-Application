package com.example.notesapplication.textnote;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapplication.R;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TextNoteExploreActivity extends AppCompatActivity {

    private ImageButton menu;
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private TextNoteDatabaseHelper textNoteDatabaseHelper;
    private TextNoteExploreRVAdapter textNoteExploreRVAdapter;
    private SearchView searchView;
    private boolean isInSelectionMode = false;
    private BottomAppBar bottomAppBar;
    private Button materialButtonSelectAll, materialButtonDelete;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_note_explore);
        menu=findViewById(R.id.createNewTextExplore);
        floatingActionButton=findViewById(R.id.floatAdd);
        recyclerView=findViewById(R.id.RVtextNoteExplore);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        textNoteDatabaseHelper=new TextNoteDatabaseHelper(this);
        searchView=findViewById(R.id.exploreSV);
        List<TextNoteData> textNoteData=getAllTextNotes();
        textNoteExploreRVAdapter=new TextNoteExploreRVAdapter(textNoteData, this);
        recyclerView.setAdapter(textNoteExploreRVAdapter);
        bottomAppBar=findViewById(R.id.bottomAppBar);
        materialButtonSelectAll=findViewById(R.id.btnSelectAll);
        materialButtonDelete=findViewById(R.id.btnDelete);
        textView=findViewById(R.id.TextNoteExpNothingTV);
        if (!getAllTextNotes().isEmpty())
            textView.setVisibility(View.GONE);
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
        if (getAllTextNotes().isEmpty())
            textView.setVisibility(View.VISIBLE);
        else textView.setVisibility(View.GONE);
        if (!searchView.getQuery().toString().isEmpty()) {
            filter(searchView.getQuery().toString());
        } else {
            // Otherwise, refresh with all notes
            textNoteExploreRVAdapter.setData(getAllTextNotes());
            textNoteExploreRVAdapter.notifyDataSetChanged();
        }

    }

    private void filter(String query) {
        List<TextNoteData> filteredList = new ArrayList<>();
        for (TextNoteData note : getAllTextNotes()) {
            if (note.getHeading().toLowerCase().contains(query.toLowerCase()) ||
                    note.getContent().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(note);
            }
        }
        textNoteExploreRVAdapter.setData(filteredList);
    }



    private void setCreateNewClickListener(){
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TextNoteExploreActivity.this, TextNoteActivity.class);
                intent.putExtra("isNewNote", true);
                startActivity(intent);
            }
        });
    }

    public List<TextNoteData> getAllTextNotes() {
        List<TextNoteData> textNoteData=new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=textNoteDatabaseHelper.getReadableDatabase();
        String[] projection = {
                TextNoteDatabaseHelper.COLUMN_ID,
                TextNoteDatabaseHelper.COLUMN_NOTE_HEADING,
                TextNoteDatabaseHelper.COLUMN_NOTE_TEXT
        };

        Cursor cursor=sqLiteDatabase.query(
                TextNoteDatabaseHelper.TABLE_NOTES,
                projection,
                null,
                null,
                null,
                null,
                TextNoteDatabaseHelper.COLUMN_ID + " DESC"
        );
        if (cursor != null&&cursor.moveToFirst()){
            do{
                @SuppressLint("Range") long id=cursor.getLong(cursor.getColumnIndex(TextNoteDatabaseHelper.COLUMN_ID));
                @SuppressLint("Range") String heading=cursor.getString(cursor.getColumnIndex(TextNoteDatabaseHelper.COLUMN_NOTE_HEADING));
                @SuppressLint("Range") String content=cursor.getString(cursor.getColumnIndex(TextNoteDatabaseHelper.COLUMN_NOTE_TEXT));
                textNoteData.add(new TextNoteData(id,heading, content));
            }while (cursor.moveToNext());
        }

        if (cursor != null){
            cursor.close();
        }
        sqLiteDatabase.close();
        return textNoteData;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        textNoteDatabaseHelper.close();
    }
    public void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.text_note_explore_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                // Handle menu item clicks here
                if (item.getItemId()==R.id.action_select && !getAllTextNotes().isEmpty()){
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

    private void updateRecyclerView() {
        textNoteExploreRVAdapter.setInSelectionMode(isInSelectionMode);
        textNoteExploreRVAdapter.notifyDataSetChanged();
        if (getAllTextNotes().isEmpty())
            textView.setVisibility(View.VISIBLE);
        else textView.setVisibility(View.GONE);
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
        for (TextNoteData note : textNoteExploreRVAdapter.getTextNoteData()) {

            if (note.isSelected()) {
                selectedItems.add(note.getId());
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
                textNoteExploreRVAdapter.setData(getAllTextNotes());
                isInSelectionMode=!isInSelectionMode;
                updateRecyclerView();
                bottomAppBar.setVisibility(View.GONE);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // If the user cancels, dismiss the dialog and do nothing
                textNoteExploreRVAdapter.setData(getAllTextNotes());
                isInSelectionMode=!isInSelectionMode;
                updateRecyclerView();
                bottomAppBar.setVisibility(View.GONE);
                dialog.dismiss();
            }
        });
        builder.show();

    }

    private void deleteFromDb(List<Long> items){
        SQLiteDatabase sqLiteDatabase=textNoteDatabaseHelper.getWritableDatabase();
        try {
            for(long noteId:items){
                sqLiteDatabase.delete(TextNoteDatabaseHelper.TABLE_NOTES, TextNoteDatabaseHelper.COLUMN_ID+"=?",
                        new String[]{String.valueOf(noteId)});
            }
        }finally {
            sqLiteDatabase.close();
        }
    }

    private void selectAllItems() {
        for (TextNoteData note : textNoteExploreRVAdapter.getTextNoteData()) {
            note.setSelected(true);
        }
        updateRecyclerView();
    }

    @Override
    public void onBackPressed() {

        if (shouldHandleBackNavigation()) {
            textNoteExploreRVAdapter.setData(getAllTextNotes());
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
