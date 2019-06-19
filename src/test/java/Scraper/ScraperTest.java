package Scraper;

import Models.*;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Selector;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

@RunWith(JUnitParamsRunner.class)
public class ScraperTest {
    private Movie lordOfTheRingsMovie;
    private Book refactoringBook;
    private Music elvisForeverMusic;
    private Scraper defaultScraper;
    private static final String MOVIE_RELATIVE_PATH = "res/TestFiles/lord_of_the_rings.html";
    private static final String MUSIC_RELATIVE_PATH = "res/TestFiles/elvis_forever.html";
    private static final String BOOK_RELATIVE_PATH = "res/TestFiles/refactoring.html";
    private static final String CATALOG_RELATIVE_PATH = "res/TestFiles/catalog.html";

    /**
     * This rule prevent scraper from going into infinite loop.
     */
    @Rule
    public Timeout scraperTimeout = new Timeout(1500, TimeUnit.MILLISECONDS);

    /**
     * For every test in this class, this method will populate default values for testing.
     */
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

    /**
     * Test if GetScrape method returns Scrape object with 1 movie when valid parameter containing movie details is passed.
     */
    @Test
    public void GetScrape_ReturnScrapeObjectWithMovie_WhenPageContainsMovieDetails() {
        // Arrange
        Movie expectedMovie = lordOfTheRingsMovie;
        Document document = this.loadDocumentFromFile(MOVIE_RELATIVE_PATH);

        // Act
        Scrape result = defaultScraper.GetScrape(document);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getId()).isNotEmpty();
        assertThat(result.getTimeStamp()).isNotNull();
        assertThat(result.getBooks().size()).isEqualTo(0);
        assertThat(result.getMusic().size()).isEqualTo(0);
        assertThat(result.getMovies().size()).isEqualTo(1);
        assertThat(result.getMovies().get(0)).isEqualTo(expectedMovie);
    }

    /**
     * Test if GetScrape method returns Scrape object with 1 book when valid parameter containing book details is passed.
     */
    @Test
    public void GetScrape_ReturnScrapeObjectWithBook_WhenPageContainsBookDetails() {
        // Arrange
        Book expectedBook = refactoringBook;
        Document document = this.loadDocumentFromFile(BOOK_RELATIVE_PATH);

        // Act
        Scrape result = defaultScraper.GetScrape(document);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getId()).isNotEmpty();
        assertThat(result.getTimeStamp()).isNotNull();
        assertThat(result.getBooks().size()).isEqualTo(1);
        assertThat(result.getMusic().size()).isEqualTo(0);
        assertThat(result.getMovies().size()).isEqualTo(0);
        assertThat(result.getBooks().get(0)).isEqualTo(expectedBook);
    }

    /**
     * Test if GetScrape method returns Scrape object with 1 music when valid parameter containing music details is passed.
     */
    @Test
    public void GetScrape_ReturnScrapeObjectWithMusic_WhenPageContainsMusicDetails() {
        // Arrange
        Music expectedMusic = elvisForeverMusic;
        Document document = this.loadDocumentFromFile(MUSIC_RELATIVE_PATH);

        // Act
        Scrape result = defaultScraper.GetScrape(document);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getId()).isNotEmpty();
        assertThat(result.getTimeStamp()).isNotNull();
        assertThat(result.getBooks().size()).isEqualTo(0);
        assertThat(result.getMusic().size()).isEqualTo(1);
        assertThat(result.getMovies().size()).isEqualTo(0);
        assertThat(result.getMusic().get(0)).isEqualTo(expectedMusic);
    }

    /**
     * Test if GetScrape method returns Scrape object with only empty list when GetScrape method cannot find any elements.
     */
    @Test
    public void GetScrape_ReturnScrapeWithEmptyList_WhenPageDoesNotContainAnyItem() {
        // Arrange
        Document document = this.loadDocumentFromFile(CATALOG_RELATIVE_PATH);

        // Act
        Scrape result = defaultScraper.GetScrape(document);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getId()).isNotEmpty();
        assertThat(result.getTimeStamp()).isNotNull();
        assertThat(result.getBooks().size()).isEqualTo(0);
        assertThat(result.getMusic().size()).isEqualTo(0);
        assertThat(result.getMovies().size()).isEqualTo(0);
    }

    /**
     * Test if Exception is caught when SelectorUnexpectedError occur caused by jsoup Document.
     */
    @Test(expected = IllegalArgumentException.class)
    public void GetScrape_ThrowIllegalArgumentException_WhenDocumentThrowSelectorUnexpectedError() {
        // Arrange
        Document document = mock(Document.class);
        when(document.selectFirst("div.media-details"))
                .thenThrow(Selector.SelectorParseException.class);

        // Act
        defaultScraper.GetScrape(document);
    }

    /**
     * Test if Exception is caught when SelectorUnexpectedError occur caused by jsoup Element.
     */
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

    /**
     * Test if Exception is thrown when null is passed as a jsoup Document.
     */
    @Test(expected = IllegalArgumentException.class)
    public void GetScrape_ThrowIllegalArgumentException_WhenDocumentParamIsNull() {
        // Act
        defaultScraper.GetScrape(null);
    }

    /**
     * Test if FindItem method returns Movie object when jsoup Document with Movie details is passed. It also
     * test with different search values.
     *
     * @param type Type like 'Name', 'Genre', etc.
     * @param value The search value.
     */
    @Test
    @Parameters(method = "getMovieSearches")
    public void FindItem_ReturnMovieObject_WhenDetailsOfPageIsMovieItem(
            String type, String value
    ) {
        // Arrange
        Document document = this.loadDocumentFromFile(MOVIE_RELATIVE_PATH);
        Movie expectedMovie = lordOfTheRingsMovie;

        // Act
        Item result = defaultScraper.FindItem(document, type, value);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getClass()).isEqualTo(Movie.class);
        assertThat(result).isEqualTo(expectedMovie);
    }

    /**
     * Test if FindItem method returns Book object when jsoup Document with Book details is passed. It also
     * test with different search values.
     *
     * @param type Type like 'Name', 'Genre', etc.
     * @param value The search value.
     */
    @Test
    @Parameters(method = "getBookSearches")
    public void FindItem_ReturnBookObject_WhenDetailsOfPageIsBookItem(
            String type, String value
    ) {
        // Arrange
        Document document = this.loadDocumentFromFile(BOOK_RELATIVE_PATH);
        Book expectedBook = refactoringBook;

        // Act
        Item result = defaultScraper.FindItem(document, type, value);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getClass()).isEqualTo(Book.class);
        assertThat(result).isEqualTo(expectedBook);
    }

    /**
     * Test if FindItem method returns Music object when jsoup Document with Music details is passed. It also
     * test with different search values.
     *
     * @param type Type like 'Name', 'Genre', etc.
     * @param value The search value.
     */
    @Test
    @Parameters(method = "getMusicSearches")
    public void FindItem_ReturnMusicObject_WhenDetailsOfPageIsMusicItem(
            String type, String value
    ) {
        // Arrange
        Document document = this.loadDocumentFromFile(MUSIC_RELATIVE_PATH);
        Music expectedMusic = elvisForeverMusic;

        // Act
        Item result = defaultScraper.FindItem(document, type, value);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getClass()).isEqualTo(Music.class);
        assertThat(result).isEqualTo(expectedMusic);
    }

    /**
     * Test if FindItem method handles null document parameter. Expect to throw IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void FindItem_ThrowIllegalArgumentException_WhenDocumentParamIsNull() {
        // Arrange
        Document document = null;
        String type = "Name";
        String value = "The Lord of the Rings";

        // Act
        defaultScraper.FindItem(document, type, value);
    }

    /**
     * Test if FindItem method handles invalid type parameter. Expect to throw IllegalArgumentException.
     *
     * @param type Invalid string.
     */
    @Test(expected = IllegalArgumentException.class)
    @Parameters(method = "getInvalidStrings")
    public void FindItem_ThrowIllegalArgumentException_WhenTypeParamIsNullOrEmpty(
            String type
    ) {
        // Arrange
        Document document = mock(Document.class);
        String value = "The Lord of the Rings";

        // Act
        defaultScraper.FindItem(document, type, value);
    }

    /**
     * Test if FindItem method handles invalid value parameter. Expect to throw IllegalArgumentException.
     *
     * @param value Invalid string.
     */
    @Test(expected = IllegalArgumentException.class)
    @Parameters(method = "getInvalidStrings")
    public void FindItem_ThrowIllegalArgumentException_WhenValueParamIsNullOrEmpty(
            String value
    ) {
        // Arrange
        Document document = mock(Document.class);
        String type = "Name";

        // Act
        defaultScraper.FindItem(document, type, value);
    }

    /**
     * Test if FindItem will return null when it cannot find anything.
     */
    @Test
    public void FindItem_ReturnNull_WhenItemCannotBeFound() {
        // Arrange
        Document document = mock(Document.class);
        String type = "Name";
        String value = "Refactoring";
        when(document.selectFirst(anyString()))
                .thenReturn(null);

        // Act
        Item result = defaultScraper.FindItem(document, type, value);

        // Assert
        verify(document).selectFirst(anyString());
        assertThat(result).isNull();
    }


    /**
     * Test if FindItem will return null when unknown search type is passed.
     */
    @Test()
    public void FindItem_ReturnNull_WhenTypeParamDoesNotExists() {
        // Arrange
        Document document = loadDocumentFromFile(MOVIE_RELATIVE_PATH);
        String invalidType = "qweHJgyugVKHgj";
        String value ="The Lord of the Rings";

        // Act
        Item result = defaultScraper.FindItem(document, invalidType, value);

        // Assert
        assertThat(result).isNull();
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

    /**
     * This method is used to parameterised test
     *
     * @return Parameters for searching for movie
     */
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

    /**
     * This method is used to parameterised test
     *
     * @return Parameters for searching for book
     */
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

    /**
     * This method is used to parameterised test
     *
     * @return Parameters for searching for music
     */
    private static final Object[] getMusicSearches() {
        return new Object[]{
                new Object[]{"Name", "Forever"},
                new Object[]{"Genre", "Rock"},
                new Object[]{"Format", "Vinyl"},
                new Object[]{"Year", "2015"},
                new Object[]{"Artist", "Elvis Presley"}
        };
    }

    /**
     * This method is used to parameterised test
     *
     * @return Returns invalid strings
     */
    private static final Object[] getInvalidStrings() {
        return new Object[]{
                new Object[]{""},
                new Object[]{" "},
                new Object[]{"          "},
                new Object[]{null},
        };
    }
}
