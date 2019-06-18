package Models;

import java.util.List;
import java.util.Objects;

public class Book extends Item{

    private String genre;
    private List<String> author;
    private String publisher;
    private String isbn;

    public String getGenre() {
        return genre;
    }

    public List<String> getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Book(String genre, List<String> author, String publisher, String isbn) {
        this.genre = genre;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
    }

    public Book(String name, Integer year, String format, String genre, List<String> author, String publisher, String isbn) {
        super(name, year, format);
        this.genre = genre;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
    }

    public Book() {
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Book book = (Book) o;
        return Objects.equals(genre, book.genre) &&
                Objects.equals(author, book.author) &&
                Objects.equals(publisher, book.publisher) &&
                Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), genre, author, publisher, isbn);
    }
}
