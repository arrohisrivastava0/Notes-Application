package com.example.notesapplication.textnote;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.text.style.UnderlineSpan;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notesapplication.R;

public class TextNoteActivity extends AppCompatActivity {
    private EditText editTextNoteHeading;
    private EditText editTextNote;
    private ImageButton btnBold;
    private ImageButton btnItalic;
    private ImageButton btnUnderline;
    private ImageButton btnIncreaseFontSize;
    private ImageButton btnDecreaseFontSize;
    private TextNoteDatabaseHelper textNoteDatabaseHelper;
    private long noteId;
    private boolean isExisting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_note);

        editTextNoteHeading=findViewById(R.id.headingTextNote);
        editTextNote = findViewById(R.id.editTextNote);
        btnBold = findViewById(R.id.btnBold);
        btnItalic = findViewById(R.id.btnItalic);
        btnUnderline = findViewById(R.id.btnUnderline);
        btnIncreaseFontSize = findViewById(R.id.btnIncreaseFontSize);
        btnDecreaseFontSize = findViewById(R.id.btnDecreaseFontSize);
        textNoteDatabaseHelper=new TextNoteDatabaseHelper(this);
        ImageButton btnSaveNote = findViewById(R.id.saveTextNote);
        setButtonClickListeners();

        boolean isNewNote = getIntent().getBooleanExtra("isNewNote", false);
        noteId=getIntent().getLongExtra("noteId",-1);
        if(noteId!=-1){
            isExisting=true;
        }
        if (isNewNote) {
            // Clear any existing note data and present a blank note
            editTextNoteHeading.setText("");
            editTextNote.setText(""); // Clear existing note content
        } else {
            // Load existing note data

            String savedNote = loadNoteFromDatabase(noteId);
            editTextNote.setText(savedNote);
            String savedNoteHead=loadNoteHeadingFromDatabase(noteId);
            editTextNoteHeading.setText(savedNoteHead);
        }

        editTextNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextNoteHeading.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnSaveNote.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (editTextNote.getText().toString().trim().isEmpty())
                    finish();

                else {
                    if(isExisting){
                        updateNoteInDatabase(noteId,editTextNote.getText().toString(), editTextNoteHeading.getText().toString());
                        finish();
                    }
                    else {
                        saveTextNoteToDatabase(editTextNote.getText().toString(), editTextNoteHeading.getText().toString());
                        Log.d("SaveButton", "Save button clicked");
                        finish();
                    }
                }
            }
        });
    }

    @SuppressLint("Range")
    private String loadNoteHeadingFromDatabase(long noteId){
        String textNoteHead = "";
        SQLiteDatabase sqLiteDatabase=textNoteDatabaseHelper.getReadableDatabase();
        String[] projection={TextNoteDatabaseHelper.COLUMN_NOTE_HEADING};
        String selection = TextNoteDatabaseHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(noteId)};
        try(Cursor cursor= sqLiteDatabase.query(
                TextNoteDatabaseHelper.TABLE_NOTES,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        ) ) {
            if (cursor != null && cursor.moveToFirst())
                textNoteHead = cursor.getString(cursor.getColumnIndex(TextNoteDatabaseHelper.COLUMN_NOTE_HEADING));
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"An error occurred", Toast.LENGTH_SHORT).show();
        }

        return textNoteHead;
    }

    @SuppressLint("Range")
    private String loadNoteFromDatabase(long noteId){
        String textNoteText = "";
        SQLiteDatabase sqLiteDatabase=textNoteDatabaseHelper.getReadableDatabase();
        String[] projection={TextNoteDatabaseHelper.COLUMN_NOTE_TEXT};
        String selection = TextNoteDatabaseHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(noteId)};
        try(Cursor cursor= sqLiteDatabase.query(
                TextNoteDatabaseHelper.TABLE_NOTES,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        ) ) {
            if (cursor.moveToFirst())
                textNoteText = cursor.getString(cursor.getColumnIndex(TextNoteDatabaseHelper.COLUMN_NOTE_TEXT));
        }catch (Exception e){
            e.printStackTrace();
        }

        return textNoteText;
    }

    private void updateNoteInDatabase(long noteId, String noteText, String noteHead) {
       SQLiteDatabase sqLiteDatabase=textNoteDatabaseHelper.getWritableDatabase();
       ContentValues contentValues=new ContentValues();
       contentValues.put(TextNoteDatabaseHelper.COLUMN_NOTE_TEXT, noteText);
       contentValues.put(TextNoteDatabaseHelper.COLUMN_NOTE_HEADING, noteHead);
       int rowsAffected = sqLiteDatabase.update(TextNoteDatabaseHelper.TABLE_NOTES, contentValues,
               TextNoteDatabaseHelper.COLUMN_ID+"=?",
               new String[]{String.valueOf(noteId)});
       if (rowsAffected>0){
           Toast.makeText(this,"Note Updated", Toast.LENGTH_SHORT).show();
       }else Toast.makeText(this, "Failed to Update", Toast.LENGTH_SHORT).show();
       sqLiteDatabase.close();
    }

    private void saveTextNoteToDatabase(String textNoteText, String textNoteHead){
        SQLiteDatabase sqLiteDatabase=textNoteDatabaseHelper.getWritableDatabase();
        ContentValues contentValuesText=new ContentValues();
        contentValuesText.put(TextNoteDatabaseHelper.COLUMN_NOTE_TEXT, textNoteText);
        contentValuesText.put(TextNoteDatabaseHelper.COLUMN_NOTE_HEADING, textNoteHead);

        long rowID=sqLiteDatabase.insertWithOnConflict(
                TextNoteDatabaseHelper.TABLE_NOTES,
                null,
                contentValuesText,
                SQLiteDatabase.CONFLICT_REPLACE
        );


        if (rowID!=-1)
            Toast.makeText(this, "Note Saved",Toast.LENGTH_SHORT).show();
        else Toast.makeText(this, "Error saving note", Toast.LENGTH_SHORT).show();

        sqLiteDatabase.close();
        finish();
    }

    private void setButtonClickListeners() {
        btnBold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                applyFormatting(Typeface.BOLD);
            }
        });


        btnItalic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyFormatting(Typeface.ITALIC);
            }
        });

        btnUnderline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyFormatting(100);
            }
        });

        btnIncreaseFontSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increaseFontSize();
            }
        });

        btnDecreaseFontSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decreaseFontSize();
            }
        });
    }

    private void applyFormatting(int style) {
        int startSelection = editTextNote.getSelectionStart();
        int endSelection = editTextNote.getSelectionEnd();

        if (startSelection != endSelection) {
            SpannableStringBuilder stringBuilder = new SpannableStringBuilder(editTextNote.getText());

            if (style == Typeface.BOLD || style == Typeface.ITALIC) {
                stringBuilder.setSpan(new StyleSpan(style), startSelection, endSelection, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            } else if (style == 100) {
                stringBuilder.setSpan(new UnderlineSpan(), startSelection, endSelection, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            }

            editTextNote.setText(stringBuilder);
            editTextNote.setSelection(endSelection);
        } else {
            Toast.makeText(this, "Select text to apply formatting", Toast.LENGTH_SHORT).show();
        }
    }


    private void increaseFontSize() {
        int startSelection = editTextNote.getSelectionStart();
        int endSelection = editTextNote.getSelectionEnd();

        if (startSelection != endSelection) {
            SpannableStringBuilder stringBuilder = new SpannableStringBuilder(editTextNote.getText());
            int currentSize = (int) editTextNote.getTextSize();
            int newSize = currentSize + 2; // Increase font size by 2 pixels

            stringBuilder.setSpan(new AbsoluteSizeSpan(newSize), startSelection, endSelection, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            editTextNote.setText(stringBuilder);
            editTextNote.setSelection(endSelection);
        } else {
            Toast.makeText(this, "Select text to apply formatting", Toast.LENGTH_SHORT).show();
        }

    }

    private void decreaseFontSize() {
        int startSelection = editTextNote.getSelectionStart();
        int endSelection = editTextNote.getSelectionEnd();

        if (startSelection != endSelection) {
            SpannableStringBuilder stringBuilder = new SpannableStringBuilder(editTextNote.getText());
            int currentSize = (int) editTextNote.getTextSize();
            int newSize = Math.max(currentSize - 2, 1); // Decrease font size by 2 pixels, with a minimum size of 1 pixel

            stringBuilder.setSpan(new AbsoluteSizeSpan(newSize), startSelection, endSelection, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            editTextNote.setText(stringBuilder);
            editTextNote.setSelection(endSelection);
        } else {
            Toast.makeText(this, "Select text to apply formatting", Toast.LENGTH_SHORT).show();
        }
    }
}

