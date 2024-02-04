package com.example.notesapplication.todo_list;

import java.util.Objects;

public class TodoListExploreData {
    private long id;
    private String heading;
    private String content;
    private boolean isSelected;

    public TodoListExploreData(long id, String heading, String content, boolean isSelected) {
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
        TodoListExploreData other = (TodoListExploreData) obj;
        return Objects.equals(heading, other.heading) && Objects.equals(content, other.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(heading, content);
    }
}

