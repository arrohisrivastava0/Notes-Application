package com.example.notesapplication.todo_list;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapplication.R;

public class TodoListRVAdapter extends RecyclerView.Adapter<TodoListRVAdapter.ViewHolder> {

    @NonNull
    @Override
    public TodoListRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TodoListRVAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTodoItemHead;
        CheckBox checkBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTodoItemHead=itemView.findViewById(R.id.TVTodoItemHead);
            checkBox=itemView.findViewById(R.id.checkTodo);
        }
    }
}
