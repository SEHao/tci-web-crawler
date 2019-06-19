import Models.*;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Selector;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnitParamsRunner.class)
public class ScraperTest {
    private Movie lordOfTheRingsMovie;
    private Book refactoringBook;
    private Music elvisForeverMusic;
    private Scraper defaultScraper;

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

        List<String> authors = new ArrayList<>();
        authors.add("Martin Fowler");
        authors.add("Kent Beck");
        authors.add("John Brant");
        authors.add("William Opdyke");
        authors.add("Don Roberts");

        refactoringBook = new Book(
                "Refactoring: Improving the Design of Existing Code",
                1999,
                "Hardcover",
                "Tech",
                authors,
                "Addison-Wesley Professional",
                "978-0201485677"
        );

        elvisForeverMusic = new Music(
                "Elvis Forever",
                2015,
                "Vinyl",
                "Elvis Presley",
                "Rock"
        );

        defaultScraper = new Scraper();
    }

    @Test
    public void GetScrape_ReturnScrapeObjectWithMovie_WhenPageContainsMovieDetails() {
        // Arrange
        Movie expectedMovie = lordOfTheRingsMovie;
        Document document = this.loadDocumentFromFile("res/TestFiles/lord_of_the_rings.html");

        // Act
        Scrape result = defaultScraper.GetScrape(document);

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
    public void GetScrape_ReturnScrapeObjectWithBook_WhenPageContainsBookDetails() {
        // Arrange
        Book expectedBook = refactoringBook;
        Document document = this.loadDocumentFromFile("res/TestFiles/refactoring.html");

        // Act
        Scrape result = defaultScraper.GetScrape(document);

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
    public void GetScrape_ReturnScrapeObjectWithMusic_WhenPageContainsMusicDetails() {
        // Arrange
        Music expectedMusic = elvisForeverMusic;
        Document document = this.loadDocumentFromFile("res/TestFiles/elvis_forever.html");

        // Act
        Scrape result = defaultScraper.GetScrape(document);

        // Assert
        assertNotNull(result);
        assertFalse(result.getId().isBlank());
        assertFalse(result.getId().isEmpty());
        assertNotNull(result.getTimeStamp());
        assertEquals(0, result.getBooks().size());
        assertEquals(1, result.getMusic().size());
        assertEquals(0, result.getMovies().size());
        assertEquals(expectedMusic, result.getMusic().get(0));
    }

    @Test
    public void GetScrape_ReturnObjectOfEmptyList_WhenPageDoesNotContainAnyItem() {
        // Arrange
        Document document = this.loadDocumentFromFile("res/TestFiles/catalog.html");

        // Act
        Scrape result = defaultScraper.GetScrape(document);

        // Assert
        assertNotNull(result);
        assertFalse(result.getId().isBlank());
        assertFalse(result.getId().isEmpty());
        assertNotNull(result.getTimeStamp());
        assertEquals(0, result.getBooks().size());
        assertEquals(0, result.getMusic().size());
        assertEquals(0, result.getMovies().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void GetScrape_ThrowIllegalArgumentException_WhenDocumentThrowSelectorUnexpectedError() {
        // Arrange
        Document document = mock(Document.class);
        when(document.selectFirst("div.media-details"))
                .thenThrow(Selector.SelectorParseException.class);

        // Act
        defaultScraper.GetScrape(document);
    }

    @Test(expected = IllegalArgumentException.class)
    public void GetScrape_ThrowIllegalArgumentException_WhenElementThrowSelectorUnexpectedError() {
        // Arrange
        Document document = mock(Document.class);
        Element element = mock(Element.class);
        when(element.select("table tbody tr"))
                .thenThrow(Selector.SelectorParseException.class);
        when(document.selectFirst("div.media-details"))
                .thenReturn(element);

        // Act
        defaultScraper.GetScrape(document);
    }

    @Test(expected = IllegalArgumentException.class)
    public void GetScrape_ThrowIllegalArgumentException_WhenDocumentParamIsNull() {
        // Act
        defaultScraper.GetScrape(null);
    }

    @Test
    @Parameters(method = "getMovieSearches")
    public void FindItem_ReturnMovieObject_WhenDetailsOfPageIsMovieItem(
            String type, String value
    ) {
        // Arrange
        Document document = this.loadDocumentFromFile("res/TestFiles/lord_of_the_rings.html");
        Movie expectedMovie = lordOfTheRingsMovie;

        // Act
        Item result = defaultScraper.FindItem(document, type, value);

        // Assert
        assertNotNull(result);
        assertEquals(Movie.class, result.getClass());
        assertEquals(expectedMovie, result);
    }

    @Test
    @Parameters(method = "getBookSearches")
    public void FindItem_ReturnBookObject_WhenDetailsOfPageIsBookItem(
            String type, String value
    ) {
        // Arrange
        Document document = this.loadDocumentFromFile("res/TestFiles/refactoring.html");
        Book expectedBook = refactoringBook;

        // Act
        Item result = defaultScraper.FindItem(document, type, value);

        // Assert
        assertNotNull(result);
        assertEquals(Book.class, result.getClass());
        assertEquals(expectedBook, result);
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


    /**
     * Load HTML file and parse it into jsoup document
     *
     * @param path HTML relative file path.
     * @return Returns jsoup document.
     */
    private Document loadDocumentFromFile(String path) {
        File htmlTemplateFile = new File(path);

        Document document = null;
        try {
            String htmlString = FileUtils.readFileToString(htmlTemplateFile, "UTF-8");
            document = Jsoup.parse(htmlString);
        } catch (IOException e) {
            fail(e.getMessage());
        }

        return document;
    }

    private static final Object[] getMovieSearches() {
        return new Object[]{
                new Object[]{"Name", "The Lord of the Rings"},
                new Object[]{"Genre", "Drama"},
                new Object[]{"Format", "Blu-ray"},
                new Object[]{"Year", "2001"},
                new Object[]{"Director", "Peter Jackson"},
                new Object[]{"Writers", "Fran Walsh"},
                new Object[]{"Stars", "Ajay Naidu"}
        };
    }

    private static final Object[] getBookSearches() {
        return new Object[]{
                new Object[]{"Name", "Refactoring"},
                new Object[]{"Genre", "Tech"},
                new Object[]{"Format", "Hardcover"},
                new Object[]{"Year", "1999"},
                new Object[]{"Authors", "Kent Beck"},
                new Object[]{"Publisher", "Addison-Wesley Professional"},
                new Object[]{"ISBN", "978-0201485677"}
        };
    }
}
