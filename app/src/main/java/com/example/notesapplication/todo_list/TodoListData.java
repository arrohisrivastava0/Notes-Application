package com.example.notesapplication.todo_list;

import com.example.notesapplication.textnote.TextNoteData;

import java.util.Objects;

public class TodoListData {
    private long id;
    private String heading;
    private String content;
    private boolean isSelected;

    public TodoListData(long id, String heading, String content, boolean isSelected) {
        this.id = id;
        this.heading = heading;
        this.content = content;
        this.isSelected = isSelected;
    }

    public long getId() {
        return id;
    }

    public String getHeading() {
        return heading;
    }

    public String getContent() {
        return content;
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
        return Objects.equals(heading, other.heading) && Objects.equals(content, other.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(heading, content);
    }
}

