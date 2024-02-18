package com.example.notesapplication.todo_list;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapplication.R;
import com.example.notesapplication.textnote.TextNoteActivity;
import com.example.notesapplication.textnote.TextNoteData;
import com.example.notesapplication.textnote.TextNoteExploreRVAdapter;

import java.util.List;

public class TodoListExploreRVAdapter extends RecyclerView.Adapter<TodoListExploreRVAdapter.ViewHolder> {
    private boolean isInSelectionMode;
    private List<TodoListExploreData> todoListExploreData;
    private Context context;
    private TodoListRVAdapter todoListRVAdapter;

    public TodoListExploreRVAdapter(List<TodoListExploreData> todoListExploreData, Context context) {
        this.todoListExploreData = todoListExploreData;
        this.context = context;
    }

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
        holder.statusTV.setText(Integer.toString(todoListRVAdapter.getItemCount())+" items left");
        holder.checkBox.setChecked(currentTodoListExploreData.isSelected());
        if (isInSelectionMode)
            holder.checkBox.setVisibility(View.VISIBLE);
        else holder.checkBox.setVisibility(View.GONE);

        if (isInSelectionMode)
            holder.checkBox.setVisibility(View.VISIBLE);
        holder.itemView.setOnClickListener(v -> {
            if (isInSelectionMode){
                holder.checkBox.setVisibility(View.VISIBLE);
                currentTodoListExploreData.setSelected(!currentTodoListExploreData.isSelected());
                holder.checkBox.setChecked(currentTodoListExploreData.isSelected());
            }else {
                holder.checkBox.setVisibility(View.GONE);
                openTodoForEditing(currentTodoListExploreData);
            }
        });
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> currentTodoListExploreData.setSelected(isChecked));
    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public void setData(List<TodoListExploreData> newData) {
        this.todoListExploreData = newData;
        notifyDataSetChanged();
    }
    public void setInSelectionMode(boolean inSelectionMode) {
        isInSelectionMode = inSelectionMode;
    }
    public void openTodoForEditing(TodoListExploreData todo) {
        Intent intent = new Intent(context, TodoListActivity.class);
        intent.putExtra("todoId", todo.getId()); // Pass the ID of the selected note to the TextNoteActivity
        context.startActivity(intent);
    }

    public List<TodoListExploreData> getTodoListData() {
        return todoListExploreData;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
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
