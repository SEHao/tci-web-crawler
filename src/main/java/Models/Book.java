package Models;

import java.util.List;

public class Book extends Item{

    public String Genre;

    public List<String> Author;

    public String Publisher;

    public String ISBN;

    public String getGenre() {
        return Genre;
    }

    public List<String> getAuthor() {
        return Author;
    }

    public String getPublisher() {
        return Publisher;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public void setAuthor(List<String> author) {
        Author = author;
    }

    public void setPublisher(String publisher) {
        Publisher = publisher;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public Book(String genre, List<String> author, String publisher, String ISBN) {
        Genre = genre;
        Author = author;
        Publisher = publisher;
        this.ISBN = ISBN;
    }

    public Book(String name, Integer year, String format, String genre, List<String> author, String publisher, String ISBN) {
        super(name, year, format);
        Genre = genre;
        Author = author;
        Publisher = publisher;
        this.ISBN = ISBN;
    }
}
