package Models;

import java.util.Objects;

public class Music extends Item {

    private String artist;
    private String genre;

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Music(String artist, String genre) {
        this.artist = artist;
        this.genre = genre;
    }

    public Music(String name, Integer year, String format, String artist, String genre) {
        super(name, year, format);
        this.artist = artist;
        this.genre = genre;
    }

    public Music() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Music music = (Music) o;
        return Objects.equals(artist, music.artist) &&
                Objects.equals(genre, music.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), artist, genre);
    }
}
