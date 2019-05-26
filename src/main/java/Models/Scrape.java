package Models;

import java.util.List;

public class Scrape {

    public String getId() {
        return Id;
    }

    public Long getTimeStamp() {
        return TimeStamp;
    }

    public List<Movie> getMovies() {
        return Movies;
    }

    public List<Models.Music> getMusic() {
        return Music;
    }

    public List<Book> getBooks() {
        return Books;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setTimeStamp(Long timeStamp) {
        TimeStamp = timeStamp;
    }

    public void setMovies(List<Movie> movies) {
        Movies = movies;
    }

    public void setMusic(List<Models.Music> music) {
        Music = music;
    }

    public void setBooks(List<Book> books) {
        Books = books;
    }

    public Scrape(String id, Long timeStamp, List<Movie> movies, List<Models.Music> music, List<Book> books) {
        Id = id;
        TimeStamp = timeStamp;
        Movies = movies;
        Music = music;
        Books = books;
    }

    public String Id;

    public Long TimeStamp;

    public List<Movie> Movies;

    public List<Music> Music;

    public List<Book> Books;


}
