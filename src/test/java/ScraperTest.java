import Models.Movie;
import Models.Scrape;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ScraperTest {
    @Test
    public void GetScrape_ReturnScrapeObject() {
        // Arrange
        Scraper scraper = new Scraper();

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

        Movie expectedMovie = new Movie(
                "The Lord of the Rings: The Fellowship of the Ring",
                2001,
                "Blu-ray",
                writers,
                "Peter Jackson",
                "Drama",
                stars
        );

        Document mockDocument = mock(Document.class);
        Element mockMediaDetailsElement = mock(Element.class);
        Elements mockMediaDetailRowsElements = mock(Elements.class);
        Element mockCategoryElement = mock(Element.class);
        Element mockGenreElement = mock(Element.class);
        Element mockFormatElement = mock(Element.class);
        Element mockYearElement = mock(Element.class);
        Element mockDirectorElement = mock(Element.class);
        Element mockWritersElement = mock(Element.class);
        Element mockStarsElement = mock(Element.class);

        when(mockDocument.selectFirst("div.media-details"))
                .thenReturn(mockMediaDetailsElement);
        when(mockMediaDetailsElement.selectFirst("h1").text())
                .thenReturn("The Lord of the Rings: The Fellowship of the Ring");
        when(mockMediaDetailsElement.select("table tbody tr"))
                .thenReturn(mockMediaDetailRowsElements);
        when(mockCategoryElement.selectFirst("th").text())
                .thenReturn("Category");
        when(mockCategoryElement.selectFirst("td").text())
                .thenReturn("Movies");
        when(mockGenreElement.selectFirst("th").text())
                .thenReturn("Genre");
        when(mockGenreElement.selectFirst("td").text())
                .thenReturn("Drama");
        when(mockFormatElement.selectFirst("th").text())
                .thenReturn("Format");
        when(mockFormatElement.selectFirst("td").text())
                .thenReturn("Blu-ray");
        when(mockYearElement.selectFirst("th").text())
                .thenReturn("Year");
        when(mockYearElement.selectFirst("td").text())
                .thenReturn("2001");
        when(mockDirectorElement.selectFirst("th").text())
                .thenReturn("Director");
        when(mockDirectorElement.selectFirst("td").text())
                .thenReturn("Peter Jackson");
        when(mockDirectorElement.selectFirst("th").text())
                .thenReturn("Writers");
        when(mockDirectorElement.selectFirst("td").text())
                .thenReturn("J.R.R. Tolkien, Fran Walsh, Philippa Boyens, Peter Jackson");
        when(mockDirectorElement.selectFirst("th").text())
                .thenReturn("Stars");
        when(mockDirectorElement.selectFirst("td").text())
                .thenReturn("Ron Livingston, Jennifer Aniston, David Herman, Ajay Naidu, Diedrich Bader, Stephen Root");

        mockMediaDetailRowsElements.add(mockCategoryElement);
        mockMediaDetailRowsElements.add(mockGenreElement);
        mockMediaDetailRowsElements.add(mockFormatElement);
        mockMediaDetailRowsElements.add(mockYearElement);
        mockMediaDetailRowsElements.add(mockDirectorElement);
        mockMediaDetailRowsElements.add(mockWritersElement);
        mockMediaDetailRowsElements.add(mockStarsElement);

        // Act
        Scrape result = scraper.GetScrape(mockDocument);

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
