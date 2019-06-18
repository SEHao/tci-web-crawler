import Models.Book;
import Models.Movie;
import Models.Scrape;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ScraperTest {
    private Movie lordOfTheRingsMovie;

    @Before
    public void setUp() {
        List<String> writers = new ArrayList<>();
        writers.add("J.R.R. Tolkien");
        writers.add("Fran Walsh");
        writers.add("Philippa Boyens");
        writers.add("Peter Jackson");

        List<String> stars = new ArrayList<>();
        stars.add("Ron Livingston");
        stars.add("Jennifer Aniston");
        stars.add("David Herman");
        stars.add("Ajay Naidu");
        stars.add("Diedrich Bader");
        stars.add("Stephen Root");

        lordOfTheRingsMovie = new Movie(
                "The Lord of the Rings: The Fellowship of the Ring",
                2001,
                "Blu-ray",
                writers,
                "Peter Jackson",
                "Drama",
                stars
        );
    }

    @Test
    public void GetScrape_ReturnScrapeObjectWithMovie() {
        // Arrange
        Scraper scraper = new Scraper();
        Movie expectedMovie = lordOfTheRingsMovie;
        File htmlTemplateFile = new File("res/TestFiles/lord_of_the_rings.html");
        Document document = null;
        try {
            String htmlString = FileUtils.readFileToString(htmlTemplateFile, "UTF-8");
            document = Jsoup.parse(htmlString);
        } catch (IOException e) {
            fail(e.getMessage());
        }

        // Act
        Scrape result = scraper.GetScrape(document);

        // Assert
        assertNotNull(result);
        assertFalse(result.getId().isBlank());
        assertFalse(result.getId().isEmpty());
        assertNotNull(result.getTimeStamp());
        assertEquals(0, result.getBooks().size());
        assertEquals(0, result.getMusic().size());
        assertEquals(1, result.getMovies().size());
        assertEquals(expectedMovie, result.getMovies().get(0));
    }

    @Test
    public void GetScrape_ReturnScrapeObjectWithBook() {
        // Arrange
        Scraper scraper = new Scraper();

        List<String> authors = new ArrayList<>();
        authors.add("Martin Fowler");
        authors.add("Kent Beck");
        authors.add("John Brant");
        authors.add("William Opdyke");
        authors.add("Don Roberts");

        Book expectedBook = new Book(
                "Refactoring: Improving the Design of Existing Code",
                1999,
                "Hardcover",
                "Tech",
                authors,
                "Addison-Wesley Professional",
                "978-0201485677"
        );

        File htmlTemplateFile = new File("res/TestFiles/refactoring.html");

        Document document = null;
        try {
            String htmlString = FileUtils.readFileToString(htmlTemplateFile, "UTF-8");
            document = Jsoup.parse(htmlString);
        } catch (IOException e) {
            fail(e.getMessage());
        }

        // Act
        Scrape result = scraper.GetScrape(document);

        // Assert
        assertNotNull(result);
        assertFalse(result.getId().isBlank());
        assertFalse(result.getId().isEmpty());
        assertNotNull(result.getTimeStamp());
        assertEquals(1, result.getBooks().size());
        assertEquals(0, result.getMusic().size());
        assertEquals(0, result.getMovies().size());
        assertEquals(expectedBook, result.getBooks().get(0));
    }

    @Test
    public void GetScrape_ThrowIllegalArgumentException_WhenDocumentParamIsNull() {
    }

    @Test
    public void GetScrape_ReturnNull_WhenDocumentContainsNoData() {
    }

    @Test
    public void FindItem_ReturnItemObject() {
    }

    @Test
    public void FindItem_ThrowIllegalArgumentException_WhenDocumentParamIsNull() {
    }

    @Test
    public void FindItem_ThrowIllegalArgumentException_WhenTypeParamIsNullOrEmpty() {
    }

    @Test
    public void FindItem_ThrowIllegalArgumentException_WhenValueParamIsNullOrEmpty() {
    }

    @Test
    public void FindItem_ReturnNull_WhenItemCannotBeFound() {
    }

    @Test
    public void FindItem_ThrowIllegalArgumentException_WhenTypeParamDoesNotExists() {
    }
}
