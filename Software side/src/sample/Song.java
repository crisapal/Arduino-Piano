package sample;

public class Song {
    private String name;
    private String lyrics;
    private String notes;


    public Song(String name, String lyrics, String notes) {
        this.name = name;
        this.lyrics = lyrics;
        this.notes = notes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
