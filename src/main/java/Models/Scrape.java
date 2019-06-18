package Models;

import java.util.List;
import java.util.Objects;

public class Scrape {
    private String id;
    private Long timeStamp;
    private List<Movie> movies;
    private List<Music> music;
    private List<Book> books;

    public String getId() {
        return id;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public List<Models.Music> getMusic() {
        return music;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public void setMusic(List<Models.Music> music) {
        this.music = music;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Scrape(String id, Long timeStamp, List<Movie> movies, List<Models.Music> music, List<Book> books) {
        this.id = id;
        this.timeStamp = timeStamp;
        this.movies = movies;
        this.music = music;
        this.books = books;
    }

    public Scrape() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Scrape scrape = (Scrape) o;
        return Objects.equals(id, scrape.id) &&
                Objects.equals(timeStamp, scrape.timeStamp) &&
                Objects.equals(movies, scrape.movies) &&
                Objects.equals(music, scrape.music) &&
                Objects.equals(books, scrape.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timeStamp, movies, music, books);
    }
}
