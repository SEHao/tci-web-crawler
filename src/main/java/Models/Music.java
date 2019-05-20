package Models;

public class Music extends Item {

    public String Artist;

    public String getArtist() {
        return Artist;
    }

    public void setArtist(String artist) {
        Artist = artist;
    }

    public Music(String artist) {
        Artist = artist;
    }

    public Music(String name, Integer year, String format, String artist) {
        super(name, year, format);
        Artist = artist;
    }
}
