package com.example.notesapplication.todo_list;

import java.util.Objects;

public class TodoListData {
    private long id;
    private String items;
    private int itemNo;
    private boolean isSelected;

    public TodoListData(long id, String items) {
        this.id = id;
        this.items = items;

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

    public int getItemNo() {
        return itemNo;
    }

    public void setItemNo() {
        this.itemNo = itemNo+1;
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


