package Models;

public class Music extends Item {

    private String artist;

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Music(String artist) {
        this.artist = artist;
    }

    public Music(String name, Integer year, String format, String artist) {
        super(name, year, format);
        this.artist = artist;
    }

    public Music() {
    }
}
