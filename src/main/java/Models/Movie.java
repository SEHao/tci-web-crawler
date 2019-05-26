package Models;

import java.util.List;

public class Movie extends Item {

    public List<String> Writters;

    public String Director;

    public String Genre;

    public List<String> Stars;

    public List<String> getWritters() {
        return Writters;
    }

    public String getDirector() {
        return Director;
    }

    public String getGenre() {
        return Genre;
    }

    public List<String> getStars() {
        return Stars;
    }

    public void setWritters(List<String> writters) {
        Writters = writters;
    }

    public void setDirector(String director) {
        Director = director;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public void setStars(List<String> stars) {
        Stars = stars;
    }

    public Movie(List<String> writters, String director, String genre, List<String> stars) {
        Writters = writters;
        Director = director;
        Genre = genre;
        Stars = stars;
    }

    public Movie(String name, Integer year, String format, List<String> writters, String director, String genre, List<String> stars) {
        super(name, year, format);
        Writters = writters;
        Director = director;
        Genre = genre;
        Stars = stars;
    }
}
