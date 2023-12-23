package com.example.notesapplication.textnote;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapplication.R;

import java.util.List;

public class TextNoteExploreRVAdapter extends RecyclerView.Adapter<TextNoteExploreRVAdapter.ViewHolder> {
    private List<TextNoteData> textNoteData;
    private Context context;

    public TextNoteExploreRVAdapter(List<TextNoteData> textNoteData, Context context) {
        this.textNoteData = textNoteData;
        this.context = context;
    }

    @NonNull
    @Override
    public TextNoteExploreRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.text_explore_activity_rv_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TextNoteExploreRVAdapter.ViewHolder holder, int position) {
        TextNoteData currentTextNoteData = textNoteData.get(position);
        holder.headingTV.setText(currentTextNoteData.getHeading());
        holder.textTV.setText(currentTextNoteData.getContent());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    TextNoteData note = textNoteData.get(position);
                    // Open the selected note for editing
                    openNoteForEditing(note);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return textNoteData.size();
    }

    public void setData(List<TextNoteData> newData) {
        this.textNoteData = newData;
        notifyDataSetChanged();
    }

    public void openNoteForEditing(TextNoteData note) {
        Intent intent = new Intent(context, TextNoteActivity.class);
        intent.putExtra("noteId", note.getId()); // Pass the ID of the selected note to the TextNoteActivity
        context.startActivity(intent);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView headingTV;
        TextView textTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            headingTV = itemView.findViewById(R.id.TVTextItemHead);
            textTV = itemView.findViewById(R.id.TVTextItemContent);
        }

    }
}



