package Models;

import java.util.List;
import java.util.Objects;

public class Movie extends Item {
    private List<String> writers;
    private String director;
    private String genre;
    private List<String> stars;

    public List<String> getWriters() {
        return writers;
    }

    public String getDirector() {
        return director;
    }

    public String getGenre() {
        return genre;
    }

    public List<String> getStars() {
        return stars;
    }

    public void setWriters(List<String> writers) {
        this.writers = writers;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setStars(List<String> stars) {
        this.stars = stars;
    }

    public Movie(List<String> writers, String director, String genre, List<String> stars) {
        this.writers = writers;
        this.director = director;
        this.genre = genre;
        this.stars = stars;
    }

    public Movie(String name, Integer year, String format, List<String> writers, String director, String genre, List<String> stars) {
        super(name, year, format);
        this.writers = writers;
        this.director = director;
        this.genre = genre;
        this.stars = stars;
    }

    public Movie() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Movie movie = (Movie) o;
        return Objects.equals(writers, movie.writers) &&
                Objects.equals(director, movie.director) &&
                Objects.equals(genre, movie.genre) &&
                Objects.equals(stars, movie.stars);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), writers, director, genre, stars);
    }
}
