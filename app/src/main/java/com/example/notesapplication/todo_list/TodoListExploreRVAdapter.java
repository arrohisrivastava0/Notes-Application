package com.example.notesapplication.todo_list;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapplication.R;

public class TodoListExploreRVAdapter extends RecyclerView.Adapter<TodoListExploreRVAdapter.ViewHolder> {

    @NonNull
    @Override
    public TodoListExploreRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TodoListExploreRVAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView headingTV, statusTV;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            headingTV=itemView.findViewById(R.id.TVTodoItemHead);
            statusTV=itemView.findViewById(R.id.TVTodoStatus);
            checkBox=itemView.findViewById(R.id.checkTodo);
        }
    }
}
