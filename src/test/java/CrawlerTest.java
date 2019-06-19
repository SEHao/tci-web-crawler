import Crawler.Crawler;
import Database.Models.Action;
import Interfaces.IDocumentRetriever;
import Interfaces.IScraper;
import Models.*;
import Scrapper.Scraper;
import org.junit.Assert;
import org.junit.Test;
import org.jsoup.nodes.Document;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.configuration.injection.MockInjection;
import java.util.ArrayList;
import java.net.URL;

import java.util.List;

public class CrawlerTest {

    /**
     * Checks if a crawler object can be created when valid parameters are passed..
     */
    @Test
    public void Crawler_Successful_WhenValidIScrapperIsPassedAndDocumentRetrieverArePassed()
    {
        // Arrange
        IScraper mockedScraper = Mockito.mock(IScraper.class);
        IDocumentRetriever mockDocumentRetriever = Mockito.mock(IDocumentRetriever.class);

        // Act -- no exception should be thrown
        Crawler crawler = new Crawler(mockedScraper,mockDocumentRetriever);
    }

    /**
     * Checks if proper exception is thrown when a crawler is created with invalid(null) parameters.
     */
    @Test(expected = IllegalArgumentException.class)
    public void Crawler_ThrowsIllegalArgumentException_WhenNullIScraperIsPassed()
    {
        // Arrange
        IScraper nullScraper = null;
        IDocumentRetriever mockDocumentRetriever = Mockito.mock(IDocumentRetriever.class);

        // Act
        Crawler crawler = new Crawler(nullScraper, mockDocumentRetriever);

        //
        Assert.fail();
    }

    /**
     * Checks if proper exception is thrown when a crawler is created with invalid(null) parameters.
     */
    @Test(expected = IllegalArgumentException.class)
    public void CrawlerThrowsIllegalArguemntException_WhenNullDocumentRetrieverIsPassed()
    {
        // Arrange
        IScraper mockedScraper = Mockito.mock(IScraper.class);
        IDocumentRetriever mockDocumentRetriever = null;
        // Act -- no exception should be thrown
        Crawler crawler = new Crawler(mockedScraper,mockDocumentRetriever);
    }

    /**
     * Checks if the properties passed in the constructor are assigned properly.
     */
    @Test
    public void Crawler_Successful_ValidateScraperIsAssigned()
    {
        // Arrange
        IScraper mockedScraper = Mockito.mock(IScraper.class);
        IDocumentRetriever mockDocumentRetriever = Mockito.mock(IDocumentRetriever.class);

        // Act -- no exception should be thrown
        Crawler crawler = new Crawler(mockedScraper, mockDocumentRetriever);

        // Assert
        Assert.assertNotNull(crawler.getScraper());
    }

    /**
     * Checks if the properties passed in the constructor are assigned properly.
     */
    @Test
    public void Crawler_Successful_ValidateDocumentRetrieverIsPassed()
    {
        // Arrange
        IScraper mockedScraper = Mockito.mock(IScraper.class);
        IDocumentRetriever mockDocumentRetriever = Mockito.mock(IDocumentRetriever.class);

        // Act -- no exception should be thrown
        Crawler crawler = new Crawler(mockedScraper, mockDocumentRetriever);

        // Assert
        Assert.assertNotNull(crawler.getDocumentRetriever());
    }

    /**
     * Checks if the a craw record can be stored inside the craw object. Expects the method to return true when operation
     * is performed with valid parameters.
     */
    @Test
    public void StoreCrawlRecord_ReturnsTrue_WhenAValidActionObjectIsPassed()
    {
        // Arrange
        Action action = new Action(1,100,1,1,1);
        action.Id = 1;
        action.PagesExplored = 1;
        action.SearchDepth = 1;
        action.TimeElapsed = 100;
        action.UniquePagesFound = 1;
        IScraper mockedScrapper = Mockito.mock(IScraper.class);
        IDocumentRetriever mockDocumentRetriever = Mockito.mock(IDocumentRetriever.class);
        Crawler crawler = new Crawler(mockedScrapper, mockDocumentRetriever);

        // Act
        Boolean result = crawler.StoreCrawRecord(action);

        // Assert
        Assert.assertTrue(result);
    }

    /**
     * Checks if proper return value is given (false) when a null record is being stored .
     */
    @Test
    public void StoreCrawlRecord_ReturnsFalse_WhenNullActionObjectIsPassed()
    {
        // Arrange
        Action action = null;

        IScraper mockedScrapper = Mockito.mock(IScraper.class);
        IDocumentRetriever mockDocumentRetriever = Mockito.mock(IDocumentRetriever.class);
        Crawler crawler = new Crawler(mockedScrapper, mockDocumentRetriever);

        // Act
        Boolean result = crawler.StoreCrawRecord(action);

        // Assert
        Assert.assertFalse(result);
    }

    /**
     * Checks if proper return value is given (false) when an action with no is being stored.
     */
    @Test
    public void StoreCrawlRecord_ReturnsFalse_WhenActionDoesNotHaveAnId()
    {
        // Arrange
        Action action = new Action(null,100L, 1,1,1);
        action.PagesExplored = 1;
        action.SearchDepth = 1;
        action.TimeElapsed = 100;
        action.UniquePagesFound = 1;
        IScraper mockedScrapper = Mockito.mock(IScraper.class);
        IDocumentRetriever mockDocumentRetriever = Mockito.mock(IDocumentRetriever.class);
        Crawler crawler = new Crawler(mockedScrapper, mockDocumentRetriever);

        // Act
        Boolean result = crawler.StoreCrawRecord(action);

        // Assert
        Assert.assertFalse(result);
    }

    /**
     * Validates craw record has been stored in the correct variable after execution of the StoreCrawRecord method.
     */
    @Test
    public void StoreCrawlRecord_ReturnsTrue_ValidatePrivateFieldHasBeenUpdated()
    {
        // Arrange
        Action action = new Action(1,100, 1,1,1);
        action.Id = 1;
        action.PagesExplored = 1;
        action.SearchDepth = 1;
        action.TimeElapsed = 100;
        action.UniquePagesFound = 1;
        IScraper mockedScrapper = Mockito.mock(IScraper.class);
        IDocumentRetriever mockDocumentRetriever = Mockito.mock(IDocumentRetriever.class);
        Crawler crawler = new Crawler(mockedScrapper, mockDocumentRetriever);

        // Act
        Boolean result = crawler.StoreCrawRecord(action);
        Action lastCraw = crawler.GetLastAction();

        // Assert
        Assert.assertTrue(result);
        Assert.assertEquals(action.getId(), lastCraw.getId());
        Assert.assertEquals(action.getPagesExplored(), lastCraw.getPagesExplored());
        Assert.assertEquals(action.getSearchDepth(), lastCraw.getSearchDepth());
        Assert.assertEquals(action.getTimeElapsed(), lastCraw.getTimeElapsed());
        Assert.assertEquals(action.getUniquePagesFound(), lastCraw.getUniquePagesFound());
    }

    /**
     * Checks if a correct scrape objectis  returned  when a a craw on a valid website is performed.
     */
    @Test
    public void CrawlWholeWebsite_ReturnsValidScrape_WhenValidUrlIsPassed()
    {
        // Arrange
        IScraper mockScrapper = Mockito.mock(IScraper.class);
        IDocumentRetriever mockDocumentRetriever = Mockito.mock(IDocumentRetriever.class);
        Crawler crawler = new Crawler(mockScrapper, mockDocumentRetriever);
        String baseUrl = "http://veryValidWebsite.nl";
        Document document = new Document(baseUrl);
        Scrape expectedScrape = new Scrape
        (
                "1",
                100L,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );

        Mockito.when(mockDocumentRetriever.GetDocument(baseUrl)).thenReturn(document);
        Mockito.when(mockScrapper.GetScrape(document)).thenReturn(expectedScrape);

        // Act
        Scrape resultingScrape = crawler.CrawWholeWebsite(baseUrl);

        // Assert
        Assert.assertEquals(expectedScrape, resultingScrape);
    }

    @Test(expected = IllegalArgumentException.class)
    public void CrawlWholeWebsite_ThrowsIllegalArgumentException_WhenInvalidUrlIsPassed()
    {
        // Arrange
        IScraper mockedScrapper = Mockito.mock(IScraper.class);
        IDocumentRetriever mockDocumentRetriever = Mockito.mock(IDocumentRetriever.class);
        Crawler crawler = new Crawler(mockedScrapper, mockDocumentRetriever);
        String invalidUrl = "thisIsAnInvalidUrl";

        // Act
        crawler.CrawWholeWebsite(null);
    }

    /**
     * Checks if proper exception is throws when a craw is started with a null address.
     */
    @Test (expected = IllegalArgumentException.class)
    public void CrawlWHoleWebsite_ThrowsIllegalArgumentException_WhenNullUrlIsPassed()
    {
        // Arrange
        IScraper mockedScrapper = Mockito.mock(IScraper.class);
        IDocumentRetriever mockDocumentRetriever = Mockito.mock(IDocumentRetriever.class);
        Crawler crawler = new Crawler(mockedScrapper, mockDocumentRetriever);
        String invalidUrl = null;

        // Act
        crawler.CrawWholeWebsite(null);

    }

    /**
     * Checks if the last action is correctly returned by the method.
     */
    @Test
    public void GetLastAction_ReturnsLastAction_Successful()
    {
        IScraper mockScrapper = Mockito.mock(IScraper.class);
        IDocumentRetriever mockDocumentRetriever = Mockito.mock(IDocumentRetriever.class);
        Crawler crawler = new Crawler(mockScrapper, mockDocumentRetriever);
        String baseUrl = "http://veryValidWebsite.nl";
        Document document = new Document(baseUrl);
        Scrape expectedScrape = new Scrape
        (
                "1",
                100L,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );

        Mockito.when(mockDocumentRetriever.GetDocument(baseUrl)).thenReturn(document);
        Mockito.when(mockScrapper.GetScrape(document)).thenReturn(expectedScrape);

        // Act
        crawler.CrawWholeWebsite(baseUrl); // this should make a scrape and
                                          // save an action in the lastActionobject
        Action lastScrape = crawler.GetLastAction();

        // Assert
        Assert.assertTrue(lastScrape.UniquePagesFound.equals(1));
        Assert.assertTrue(lastScrape.PagesExplored.equals(1));
    }

    @Test
    public void GetLastAction_ReturnsNull_WhenLastActionIsNotInitialized()
    {
        // Arrange
        IScraper mockScraper = new Mockito().mock(IScraper.class);
        IDocumentRetriever mockDocumentRetriever = Mockito.mock(IDocumentRetriever.class);
        Crawler crawler = new Crawler(mockScraper, mockDocumentRetriever);

        // Act
        Action lastScrape = crawler.GetLastAction();

        // Assert
        Assert.assertNull(lastScrape);
    }

    /**
     * Checks if a found item during the scrape can be correctly returned.
     */
    @Test
    public void FindItem_ReturnsFoundItemSuccessful()
    {
        // Arrange
        String baseUrl = "http://website.com";
        Document document = new Document(baseUrl);
        IScraper mockScraper = Mockito.mock(IScraper.class);
        IDocumentRetriever mockDocumentRetriever = Mockito.mock(IDocumentRetriever.class);
        Crawler crawler = new Crawler(mockScraper, mockDocumentRetriever);
        String itemType = "Book";
        String itemName = "The cool book";
        List<String> authors = new ArrayList<>();
        authors.add("MIroslav Zahariev");
        Book bookToReturn = new Book(
                "Fantasy",
                authors,
                "Books.nl",
                "111"
        );

        Mockito.when(mockScraper.FindItem(document, itemType, itemName)).thenReturn(bookToReturn);
        Mockito.when(mockDocumentRetriever.GetDocument(baseUrl)).thenReturn(document);

        // Act
        Item resultingItem = crawler.FindItem(baseUrl, itemType, itemName);

        // Assert
        Assert.assertTrue(resultingItem.getClass().getTypeName() == Book.class.getTypeName());
        Assert.assertEquals(bookToReturn, resultingItem);
    }

    /**
     * Checks if proper exception is thrown whe FindItem is called with an invalid Url.
     */
    @Test(expected = IllegalArgumentException.class)
    public void FindItem_ThrowsIllegalArgumentException_WhenBaseUrlIsInvalid()
    {
        // Arrange
        String baseUrl = "InvalidUrl";
        IScraper mockScraper = Mockito.mock(IScraper.class);
        IDocumentRetriever mockDocumentRetriever = Mockito.mock(IDocumentRetriever.class);
        Crawler crawler = new Crawler(mockScraper, mockDocumentRetriever);
        String itemType = "book";
        String itemName = "bookBook";

        // Act
        crawler.FindItem(baseUrl, itemType, itemName);
    }

    /**
     * Checks if proper exception is thrown whe FindItem is called with a null Url.
     */
    @Test (expected = IllegalArgumentException.class)
    public void FindItem_ThrowsIllegalArgumentException_WhenBaseUrlIsNull()
    {
        // Arrange
        String baseUrl = null;
        IScraper mockScraper = Mockito.mock(IScraper.class);
        IDocumentRetriever mockDocumentRetriever = Mockito.mock(IDocumentRetriever.class);
        Crawler crawler = new Crawler(mockScraper, mockDocumentRetriever);
        String itemType = "book";
        String itemName = "bookBook";

        // Act
        crawler.FindItem(baseUrl, itemType, itemName);
    }

    /**
     * Checks if proper exception is thrown whe FindItem is called with an invalid item type (not book,movie or music).
     */
    @Test (expected = IllegalArgumentException.class)
    public void FindItem_ThrowsIllegalArgumentException_WhenItemTypeIsNotValid()
    {
        // Arrange
        String baseUrl = "http://website.com";
        IScraper mockScraper = Mockito.mock(IScraper.class);
        IDocumentRetriever mockDocumentRetriever = Mockito.mock(IDocumentRetriever.class);
        Crawler crawler = new Crawler(mockScraper, mockDocumentRetriever);
        String itemType = "Car"; // parameter type is invalid
                                // suported are book, movie, music
        String itemName = "bookBook";

        // Act
        crawler.FindItem(baseUrl, itemType, itemName);
    }

    /**
     * Checks if proper exception is thrown whe FindItem is called with an null item type.
     */
    @Test (expected = IllegalArgumentException.class)
    public void FindItem_ThrowsIllegalArgumentException_WhenItemTypeIsNull()
    {
        // Arrange
        String baseUrl = "http://website.com";
        IScraper mockScraper = Mockito.mock(IScraper.class);
        IDocumentRetriever mockDocumentRetriever = Mockito.mock(IDocumentRetriever.class);
        Crawler crawler = new Crawler(mockScraper, mockDocumentRetriever);
        String itemType = null;
        String itemName = "bookBook";

        // Act
        crawler.FindItem(baseUrl, itemType, itemName);
    }

    /**
     * Checks if proper exception is thrown whe FindItem is called with an empty item type.
     */
    @Test (expected = IllegalArgumentException.class)
    public void FindItem_ThrowsIllegalArgumentException_WhenItemValueIsEmpty()
    {
        // Arrange
        String baseUrl = "http://website.com";
        IScraper mockScraper = Mockito.mock(IScraper.class);
        IDocumentRetriever mockDocumentRetriever = Mockito.mock(IDocumentRetriever.class);
        Crawler crawler = new Crawler(mockScraper, mockDocumentRetriever);
        String itemType = "book";
        String itemName = "";

        // Act
        crawler.FindItem(baseUrl, itemType, itemName);
    }

    /**
     * Checks if proper exception is thrown whe FindItem is called with a search value of null.
     */
    @Test (expected = IllegalArgumentException.class)
    public void FindItem_ThrowsIllegalArgumentException_WhenItemValueIsNull()
    {
        // Arrange
        String baseUrl = "http://website.com";
        IScraper mockScraper = Mockito.mock(IScraper.class);
        IDocumentRetriever mockDocumentRetriever = Mockito.mock(IDocumentRetriever.class);
        Crawler crawler = new Crawler(mockScraper, mockDocumentRetriever);
        String itemType = "book";
        String itemName = null;

        // Act
        crawler.FindItem(baseUrl, itemType, itemName);
    }

    /**
     * Checks if CrawWebiste returns expected results when called with valid parameters.
     */
    @Test
    public void CrawlWebsite_ReturnsScrape_Successful()
    {
        // Arrange
        IScraper mockScraper = Mockito.mock(IScraper.class);
        IDocumentRetriever mockDocumentRetriever = Mockito.mock(IDocumentRetriever.class);
        Crawler crawler = new Crawler(mockScraper, mockDocumentRetriever);
        String baseUrl = "http://website.com";
        Document document = new Document(baseUrl);
        Action action = new Action(1, 100L, 1,1,1);

        List<Book> books = new ArrayList<>();
        List<Music> music = new ArrayList<>();
        List<Movie> movies = new ArrayList<>();

        List<String> writters = new ArrayList<>();
        writters.add("Jack Johnson");

        List<String> stars = new ArrayList<>();
        stars.add("Johny Depp");

        Movie movie = new Movie(
                writters,
                "John Smith",
                "Thriller",
                stars
        );
        movies.add(movie);

        Scrape initialScrape = new Scrape(
                "1",
                100L,
                movies,
                music,
                books
        );

        Scrape newScrape = initialScrape;
        music.add(new Music("Song", 1995, "mp3", "Group", "pop" ));
        newScrape.setMusic(music);

        Mockito.when(mockDocumentRetriever.GetDocument(baseUrl)).thenReturn(document);
        Mockito.when(mockScraper.GetScrape(document)).thenReturn(newScrape);

        // Act
        Scrape resultingScrape = crawler.CrawWebsite(baseUrl, initialScrape, action);

        // Assert
        Assert.assertFalse(resultingScrape.getMovies().isEmpty());
        Assert.assertFalse(resultingScrape.getMusic().isEmpty());
        Assert.assertEquals("John Smith", resultingScrape.getMovies().get(0).getDirector());
        Assert.assertEquals("Group" ,resultingScrape.getMusic().get(0).getArtist());
    }

    /**
     * Checks if proper exception is thrown whe CrawWebsite is called with a null URl.
     */
    @Test (expected = IllegalArgumentException.class)
    public void CrawlWebsite_ThrowsIllegalArgumentException_WhenBaseUrlIsNull()
    {
        // Arrange
        IScraper mockScraper = Mockito.mock(IScraper.class);
        IDocumentRetriever mockRetriever = Mockito.mock(IDocumentRetriever.class);
        Crawler crawler = new Crawler(mockScraper, mockRetriever);
        String baseUrl = null;

        Action action = new Action(1, 100L, 1,1,1);

        List<Book> books = new ArrayList<>();
        List<Music> music = new ArrayList<>();
        List<Movie> movies = new ArrayList<>();

        List<String> writters = new ArrayList<>();
        writters.add("Jack Johnson");

        List<String> stars = new ArrayList<>();
        stars.add("Johny Depp");

        Movie movie = new Movie(
                writters,
                "John Smith",
                "Thriller",
                stars
        );
        movies.add(movie);

        Scrape initialScrape = new Scrape(
                "1",
                100L,
                movies,
                music,
                books
        );

        // Act
        crawler.CrawWebsite(baseUrl, initialScrape, action);
    }

    /**
     * Checks if proper exception is thrown whe CrawWebsite is called with an invalid url.
     */
    @Test (expected = IllegalArgumentException.class)
    public void CrawlWebsite_ThrowsIllegalArgumentException_WhenBaseUrlIsNotValid()
    {
        // Arrange
        IScraper mockScraper = Mockito.mock(IScraper.class);
        IDocumentRetriever mockRetriever = Mockito.mock(IDocumentRetriever.class);
        Crawler crawler = new Crawler(mockScraper, mockRetriever);
        String baseUrl = "not a valid URL";

        Action action = new Action(1, 100L, 1,1,1);

        List<Book> books = new ArrayList<>();
        List<Music> music = new ArrayList<>();
        List<Movie> movies = new ArrayList<>();

        List<String> writters = new ArrayList<>();
        writters.add("Jack Johnson");

        List<String> stars = new ArrayList<>();
        stars.add("Johny Depp");

        Movie movie = new Movie(
                writters,
                "John Smith",
                "Thriller",
                stars
        );
        movies.add(movie);

        Scrape initialScrape = new Scrape(
                "1",
                100L,
                movies,
                music,
                books
        );

        // Act
        crawler.CrawWebsite(baseUrl, initialScrape, action);
    }

    /**
     * Checks if proper exception is thrown whe CrawWebsite is called with an null value for the current scrape.
     * Not allowed since the current scrape is the one that will be updated during the craw. Updating a null object
     * will result in an unwanted exception.
     */
    @Test (expected = IllegalArgumentException.class)
    public void CrawlWebsite_ThrowsIllegalArgumentException_WhenCurrentScrapeIsNull()
    {
        // Arrange
        IScraper mockScraper = Mockito.mock(IScraper.class);
        IDocumentRetriever mockRetriever = Mockito.mock(IDocumentRetriever.class);
        Crawler crawler = new Crawler(mockScraper, mockRetriever);
        String baseUrl = "not a valid URL";

        Action action = new Action(1, 100L, 1,1,1);

        List<Book> books = new ArrayList<>();
        List<Music> music = new ArrayList<>();
        List<Movie> movies = new ArrayList<>();

        List<String> writters = new ArrayList<>();
        writters.add("Jack Johnson");

        List<String> stars = new ArrayList<>();
        stars.add("Johny Depp");

        Movie movie = new Movie(
                writters,
                "John Smith",
                "Thriller",
                stars
        );
        movies.add(movie);

        Scrape initialScrape = new Scrape(
                "1",
                100L,
                movies,
                music,
                books
        );

        // Act
        crawler.CrawWebsite(baseUrl, initialScrape, null);
    }

    /**
     * Checks if proper exception is thrown whe CrawWebsite is called with an null value for the Action parameter.
     * This is done since the action will be updated during the craw.
     */
    @Test (expected = IllegalArgumentException.class)
    public void CrawlWebsite_ThrowsIllegalArgumentException_WhenCurrentActionIsNull()
    {
        // Arrange
        IScraper mockScraper = Mockito.mock(IScraper.class);
        IDocumentRetriever mockRetriever = Mockito.mock(IDocumentRetriever.class);
        Crawler crawler = new Crawler(mockScraper, mockRetriever);
        String baseUrl = "not a valid URL";

        Action action = new Action(1, 100L, 1,1,1);

        // Act
        crawler.CrawWebsite(baseUrl, null, action);
    }

    /**
     * Checks if proper the latest scrape stays unchaged when no new data is found on the current page.
     */
    @Test
    public void CrawlWebsite_DoesNotUpdateScrape_WhenThereIsNoNewDataOnPageToScrape()
    {
        // Arrange
        IScraper mockScraper = Mockito.mock(IScraper.class);
        IDocumentRetriever mockDocumentRetriever = Mockito.mock(IDocumentRetriever.class);
        Crawler crawler = new Crawler(mockScraper, mockDocumentRetriever);
        String baseUrl = "http://website.com";
        Document document = new Document(baseUrl);
        Action action = new Action(1, 100L, 1,1,1);

        List<Book> books = new ArrayList<>();
        List<Music> music = new ArrayList<>();
        List<Movie> movies = new ArrayList<>();

        List<String> writters = new ArrayList<>();
        writters.add("Jack Johnson");

        List<String> stars = new ArrayList<>();
        stars.add("Johny Depp");

        Movie movie = new Movie(
                writters,
                "John Smith",
                "Thriller",
                stars
        );
        movies.add(movie);

        Scrape initialScrape = new Scrape(
                "1",
                100L,
                movies,
                music,
                books
        );

        // Scraper will return scrape with no new data
        Scrape newScrape = new Scrape(
                "1", 100L,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>());


        Mockito.when(mockDocumentRetriever.GetDocument(baseUrl)).thenReturn(document);
        Mockito.when(mockScraper.GetScrape(document)).thenReturn(newScrape);

        // Act
        Scrape resultingScrape = crawler.CrawWebsite(baseUrl, initialScrape, action);

        // Assert
        Assert.assertFalse(resultingScrape.getMovies().isEmpty());
        Assert.assertTrue(resultingScrape.getMusic().isEmpty()); // no music was added this should be empty
        Assert.assertEquals("John Smith", resultingScrape.getMovies().get(0).getDirector());
    }

    /**
     * Checks if A craw is performed for all documents until an expected depth.
     */
    @Test
    public void CrawlWebsite_Successful_UntilSpecifiedDept()
    {
        // Arrange
        IScraper mockScraper = Mockito.mock(IScraper.class);
        IDocumentRetriever mockDocumentRetriever = Mockito.mock(IDocumentRetriever.class);
        Crawler crawler = new Crawler(mockScraper, mockDocumentRetriever);
        String baseUrl = "http://website.com";
        String secondbaseUrl = "http://secondWebsite.com";
        Document document = new Document(baseUrl);
        Document secondDocument = new Document(secondbaseUrl);

        List<Book> books = new ArrayList<>();
        List<Music> music = new ArrayList<>();
        List<Movie> movies = new ArrayList<>();

        List<String> writters = new ArrayList<>();
        writters.add("Jack Johnson");

        List<String> stars = new ArrayList<>();
        stars.add("Johny Depp");

        Movie movie = new Movie(
                writters,
                "John Smith",
                "Thriller",
                stars
        );
        movies.add(movie);

        Scrape initialScrape = new Scrape(
                "1",
                100L,
                movies,
                music,
                books
        );

        Scrape secondScrape = initialScrape;
        music.add(new Music("Song", 1995, "mp3", "Group", "pop" ));
        secondScrape.setMusic(music);
//        String name, Integer year, String format, String genre, List<String> author, String publisher, String ISBN
        books.add(new Book(
                "The new Book",
                1996,
                "electronic",
                "History",
                new ArrayList<>(),
                "books.nl",
                "1234")
        );
        Scrape thirdScrape = new Scrape("3",100L, new ArrayList<>(), new ArrayList<>(), books);

        Mockito.when(mockDocumentRetriever.GetDocument(baseUrl)).thenReturn(document);
        Mockito.when(mockDocumentRetriever.GetDocument(secondbaseUrl)).thenReturn(secondDocument);
        Mockito.when(mockScraper.GetScrape(document)).thenReturn(secondScrape);
        Mockito.when(mockScraper.GetScrape(secondDocument)).thenReturn(thirdScrape);

        // Act
        Scrape resultingScrape = crawler.CrawWholeWebsite(baseUrl);

        // Assert
        Assert.assertFalse(resultingScrape.getMovies().isEmpty());
        Assert.assertFalse(resultingScrape.getMusic().isEmpty());
        Assert.assertEquals("John Smith", resultingScrape.getMovies().get(0).getDirector());
        Assert.assertEquals("Group" ,resultingScrape.getMusic().get(0).getArtist());
    }

    /**
     * Checks if an unchanged scrape is returned when a document could not be retrieved from the passed url.
     */
    @Test
    public void CrawWebsite_ReturnsLatestScrape_WhenFoundDocumentIsNull(){

        // Arrange
        IScraper mockScraper = new Mockito().mock(IScraper.class);
        IDocumentRetriever mockDocumentRetriever = new Mockito().mock(IDocumentRetriever.class);
        Crawler crawler = new Crawler(mockScraper, mockDocumentRetriever);
        Action action = new Action(1, 100L, 1,1,1);
        String baseUrl = "http://baseurl.com";
        List<Book> books = new ArrayList<>();
        List<Music> music = new ArrayList<>();
        List<Movie> movies = new ArrayList<>();

        List<String> writters = new ArrayList<>();
        writters.add("Jack Johnson");

        List<String> stars = new ArrayList<>();
        stars.add("Johny Depp");

        Movie movie = new Movie(
                writters,
                "John Smith",
                "Thriller",
                stars
        );
        movies.add(movie);

        Scrape initialScrape = new Scrape(
                "1",
                100L,
                movies,
                music,
                books
        );

        Mockito.when(mockDocumentRetriever.GetDocument(baseUrl)).thenReturn(null);

        // Act
        Scrape resultingScrape = crawler.CrawWebsite(baseUrl, initialScrape, action);

        // Assert
        Assert.assertEquals(initialScrape, resultingScrape);
    }

    /**
     * Checks an unchanged scrape is returned when a craw is performed on a url outside of the original domain
     * Thus preventing infinite scrapes of websites.
     */
    @Test
    public void CrawWebsite_ReturnsLatestCraw_WhenProvidedUrlIsNotPartOfDomain(){

        // Arrange
        IScraper mockScraper = new Mockito().mock(IScraper.class);
        IDocumentRetriever mockDocumentRetriever = new Mockito().mock(IDocumentRetriever.class);
        Crawler crawler = new Crawler(mockScraper, mockDocumentRetriever);
        Action action = new Action(1, 100L, 1,1,1);
        String baseUrl = "http://baseurl.com";
        List<Book> books = new ArrayList<>();
        List<Music> music = new ArrayList<>();
        List<Movie> movies = new ArrayList<>();

        List<String> writters = new ArrayList<>();
        writters.add("Jack Johnson");

        List<String> stars = new ArrayList<>();
        stars.add("Johny Depp");

        Movie movie = new Movie(
                writters,
                "John Smith",
                "Thriller",
                stars
        );
        movies.add(movie);

        Scrape initialScrape = new Scrape(
                "1",
                100L,
                movies,
                music,
                books
        );

        crawler.SetDomainName("wrongDomainName.com");

        //Act
        Scrape resultingScrape = crawler.CrawWebsite(baseUrl, initialScrape, action);

        // Assert
        Assert.assertEquals(initialScrape, resultingScrape);
    }

    /**
     * Checks an null is returned when a FindItem is performed on a url outside of the original domain
     * Thus preventing infinite scrapes of websites.
     */
    @Test
    public void FindItem_ReturnsNull_WhenProvidedUrlIsNotPartOfDomain(){

        // Arrange
        IScraper scraper = Mockito.mock(IScraper.class);
        IDocumentRetriever documentRetriever = Mockito.mock(IDocumentRetriever.class);
        Crawler crawler = new Crawler(scraper, documentRetriever);
        crawler.SetDomainName("badDomain");
        String baseURl = "http://goodDomain.com";

        // Act
        Item result = crawler.FindItem(baseURl, "book", "theBook");

        // Assert
        Assert.assertNull(result);
    }

//    @Test
//    public void DoStuff(){
//        try
//        {
//            IScraper Scrapper = new Scraper();
//
//            IDocumentRetriever documentRetriever = new DocumentRetriever();
//
//            Crawler crawler = new Crawler(Scrapper, documentRetriever);
//
//            Scrape result =  crawler.CrawWholeWebsite("http://localhost/sample_site_to_crawl/");
//
//            if(result != null){
//                System.out.println();
//            }
//        }
//        catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//    }
}

