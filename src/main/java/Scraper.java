import Interfaces.IScraper;
import Models.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.Timestamp;
import java.util.*;

public class Scraper implements IScraper {

    /**
     * Scrapes a Document produced by Jsoup and returns a Scrape object.
     *
     * @param document The Jsoup document that should be scraped.
     * @return Returns the scrape object after the craw has been completed.
     * Returns null if the document is empty.
     */
    @Override
    public Scrape GetScrape(Document document) {
        if (document == null) {
            throw new IllegalArgumentException();
        }

        Date currentDate = new Date();
        Timestamp timestamp = new Timestamp(currentDate.getTime());
        Long timeStampLong = timestamp.getTime();
        List<Movie> movies = new ArrayList<>();
        List<Music> musics = new ArrayList<>();
        List<Book> books = new ArrayList<>();

        Scrape scrape = new Scrape();
        scrape.setId(UUID.randomUUID().toString());
        scrape.setTimeStamp(timeStampLong);
        scrape.setMovies(movies);
        scrape.setMusic(musics);
        scrape.setBooks(books);

        Element mediaDetails = document.selectFirst("div.media-details");

        if (mediaDetails == null) {
            return scrape;
        }

        Elements rows = mediaDetails.select("table tbody tr");

        String category = "";
        for (Element row : rows) {
            String key = row.selectFirst("th").text();
            if (key.equals("Category")) {
                category = row.selectFirst("td").text();
                break;
            }
        }

        switch (category) {
            case "Movies": {
                Movie parsedMovie = this.parseMovie(mediaDetails);
                movies.add(parsedMovie);
                break;
            }
            case "Books": {
                Book parsedBook = this.parseBook(mediaDetails);
                books.add(parsedBook);
                break;
            }
            case "Music": {
                Music music = this.parseMusic(mediaDetails);
                musics.add(music);
                break;
            }
        }

        return scrape;
    }

    /**
     * Parse Element containing movie information into movie object.
     *
     * @param mediaDetails Element which contains the details of movie.
     * @return Returns Movie object which was parsed from element.
     */
    private Movie parseMovie(Element mediaDetails) {
        Movie movie = new Movie();

        movie.setName(mediaDetails.selectFirst("h1").text());

        Elements rows = mediaDetails.select("table tbody tr");

        for (Element row : rows) {
            String key = row.selectFirst("th").text();
            String value = row.selectFirst("td").text();
            switch (key) {
                case "Genre": {
                    movie.setGenre(value);
                    break;
                }
                case "Format": {
                    movie.setFormat(value);
                    break;
                }
                case "Year": {
                    movie.setYear(Integer.parseInt(value));
                    break;
                }
                case "Director": {
                    movie.setDirector(value);
                    break;
                }
                case "Writers": {
                    movie.setWriters(Arrays.asList(value.split(", ")));
                    break;
                }
                case "Stars": {
                    movie.setStars(Arrays.asList(value.split(", ")));
                    break;
                }
            }
        }

        return movie;
    }

    /**
     * Parse Element containing book information into book object.
     *
     * @param mediaDetails Element which contains the details of book.
     * @return Returns Book object which was parsed from element.
     */
    private Book parseBook(Element mediaDetails) {
        Book book = new Book();

        book.setName(mediaDetails.selectFirst("h1").text());

        Elements rows = mediaDetails.select("table tbody tr");

        for (Element row : rows) {
            String key = row.selectFirst("th").text();
            String value = row.selectFirst("td").text();
            switch (key) {
                case "Genre": {
                    book.setGenre(value);
                    break;
                }
                case "Format": {
                    book.setFormat(value);
                    break;
                }
                case "Year": {
                    book.setYear(Integer.parseInt(value));
                    break;
                }
                case "Authors": {
                    book.setAuthor(Arrays.asList(value.split(", ")));
                    break;
                }
                case "Publisher": {
                    book.setPublisher(value);
                    break;
                }
                case "ISBN": {
                    book.setIsbn(value);
                }
            }
        }

        return book;
    }

    /**
     * Parse Element containing music information into music object.
     *
     * @param mediaDetails Element which contains the details of music.
     * @return Returns Music object which was parsed from element.
     */
    private Music parseMusic(Element mediaDetails) {
        Music music = new Music();

        Elements rows = mediaDetails.select("table tbody tr");

        music.setName(mediaDetails.selectFirst("h1").text());

        for (Element row : rows) {
            String key = row.selectFirst("th").text();
            String value = row.selectFirst("td").text();
            switch (key) {
                case "Genre": {
                    music.setGenre(value);
                    break;
                }
                case "Format": {
                    music.setFormat(value);
                    break;
                }
                case "Year": {
                    music.setYear(Integer.parseInt(value));
                    break;
                }
                case "Artist": {
                    music.setArtist(value);
                    break;
                }
            }
        }

        return music;
    }

    /**
     * Finds an item (Music, Movie or Book) in a Document produced by Jsop.
     *
     * @param document The document that the item has to be found in.
     * @param type     The type of the item that has to be found (Music, Movie, Book)
     * @param value    The name of the item that has to be found.
     * @return Returns an Item if it can be found in the document. Returns null if the item can't be found.
     */
    @Override
    public Item FindItem(Document document, String type, String value) {
        return null;
    }
}
