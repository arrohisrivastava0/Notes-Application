package com.example.notesapplication.textnote;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.widget.SearchView;
;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapplication.R;

import java.util.ArrayList;
import java.util.List;

public class TextNoteExploreActivity extends AppCompatActivity {

    private ImageButton createNew;
    private RecyclerView recyclerView;
    private TextNoteDatabaseHelper textNoteDatabaseHelper;
    private TextNoteExploreRVAdapter textNoteExploreRVAdapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_note_explore);
        createNew=findViewById(R.id.createNewTextExplore);
        recyclerView=findViewById(R.id.RVtextNoteExplore);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        textNoteDatabaseHelper=new TextNoteDatabaseHelper(this);
        searchView=findViewById(R.id.exploreSV);
        List<TextNoteData> textNoteData=getAllTextNotes();
        textNoteExploreRVAdapter=new TextNoteExploreRVAdapter(textNoteData, this);
        recyclerView.setAdapter(textNoteExploreRVAdapter);

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
    }

    @Override
    protected void onResume() {
        super.onResume();

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
        createNew.setOnClickListener(new View.OnClickListener() {
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
}
