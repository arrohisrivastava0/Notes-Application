package com.example.notesapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import com.example.notesapplication.textnote.TextNoteExploreActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridLayout gridLayout=findViewById(R.id.gridLayout);
        setButtonClickListener(gridLayout, R.id.btnNotes, TextNoteExploreActivity.class);
    }

    public void setButtonClickListener(GridLayout gridLayout, int buttonId, final Class<?> destinationClass){
        Button button=findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, destinationClass);
                startActivity(intent);
            }
        });
    }
}