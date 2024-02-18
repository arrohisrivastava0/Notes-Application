package com.example.notesapplication.todo_list;

import java.util.Objects;

public class TodoListExploreData {
    private long id;
    private String heading;
    private String noItems;
    private boolean isSelected;

    public TodoListExploreData(long id, String heading, String noItems) {
        this.id = id;
        this.heading = heading;
        this.noItems = noItems;
    }

    public long getId() {
        return id;
    }

    public String getHeading() {
        return heading;
    }

    public String getNoItems() {
        return noItems;
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
        return Objects.equals(heading, other.heading) && Objects.equals(noItems, other.noItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(heading, noItems);
    }
}

