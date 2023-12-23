package com.example.notesapplication.textnote;

import java.util.Objects;

public class TextNoteData {
    private long id;
    private String heading;
    private String content;

    public TextNoteData(long id, String heading, String content) {
        this.id=id;
        this.heading = heading;
        this.content = content;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TextNoteData other = (TextNoteData) obj;
        return Objects.equals(heading, other.heading) && Objects.equals(content, other.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(heading, content);
    }
}
