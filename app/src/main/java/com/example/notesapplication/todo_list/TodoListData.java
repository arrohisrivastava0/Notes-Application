package com.example.notesapplication.todo_list;

import java.util.Objects;

public class TodoListData {
    private long id;
    private String items;
    private boolean isSelected;
    private boolean isItemSelected;

    public TodoListData(long id, String items, boolean isSelected, boolean isItemSelected) {
        this.id = id;
        this.items = items;
        this.isSelected = isSelected;
        this.isItemSelected=isItemSelected;
    }

    public long getId() {
        return id;
    }
    public String getItems() {
        return items;
    }
    public boolean isSelected() {
        return isSelected;
    }
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TodoListData other = (TodoListData) obj;
        return Objects.equals(items, other.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }
}


