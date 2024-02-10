package com.example.notesapplication.todo_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapplication.R;
import com.example.notesapplication.textnote.TextNoteExploreRVAdapter;

import java.util.List;

public class TodoListExploreRVAdapter extends RecyclerView.Adapter<TodoListExploreRVAdapter.ViewHolder> {
    private boolean isInSelectionMode;
    private List<TodoListExploreData> todoListExploreData;

    @NonNull
    @Override
    public TodoListExploreRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_explore_activity_rv_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoListExploreRVAdapter.ViewHolder holder, int position) {
        TodoListExploreData currentTodoListExploreData=todoListExploreData.get(position);
        holder.headingTV.setText(currentTodoListExploreData.getHeading());
    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public void setInSelectionMode(boolean inSelectionMode) {
        isInSelectionMode = inSelectionMode;
    }

    public List<TodoListExploreData> getTodoListData() {
        return todoListExploreData;
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
