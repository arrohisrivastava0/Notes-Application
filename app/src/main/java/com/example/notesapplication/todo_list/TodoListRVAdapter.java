package com.example.notesapplication.todo_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapplication.R;
import com.example.notesapplication.textnote.TextNoteData;

import java.util.List;

public class TodoListRVAdapter extends RecyclerView.Adapter<TodoListRVAdapter.ViewHolder> {
    private List<TodoListData> todoListData;
    @NonNull
    @Override
    public TodoListRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_list_rv_items, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull TodoListRVAdapter.ViewHolder holder, int position) {
        TodoListData currentTodoListData=todoListData.get(position);
        holder.tvTodoItemHead.setText(currentTodoListData.getItems());
        holder.checkBox.setChecked(currentTodoListData.isSelected());
        holder.itemView.setOnClickListener(v -> {
            currentTodoListData.setSelected(!currentTodoListData.isSelected());
            holder.checkBox.setChecked(currentTodoListData.isSelected());
        });
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked)
                -> currentTodoListData.setSelected(isChecked));
    }
    @Override
    public int getItemCount() {
        return todoListData.size();
    }
    public List<TodoListData> getTodoListData() {
        return todoListData;
    }
    public void setData(List<TodoListData> newData) {
        this.todoListData = newData;
        notifyDataSetChanged();
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
