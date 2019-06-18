import Database.Models.Action;
import Interfaces.IScraper;
import Models.*;
import org.junit.Assert;
import org.junit.Test;
import org.jsoup.nodes.Document;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.configuration.injection.MockInjection;

import java.util.ArrayList;
import java.util.List;


public class CrawlerTest {

    @Test
    public void Crawler_Successful_WhenValidIScrapperIsPassed()
    {
        // Arrange
        IScraper mockedScraper = Mockito.mock(IScraper.class);

        // Act -- no exception should be thrown
        Crawler crawler = new Crawler(mockedScraper);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Crawler_ThrowsIllegalArgumentException_WhenNullIScraperIsPassed()
    {
        // Arrange
        IScraper nullScraper = null;

        // Act
        Crawler crawler = new Crawler(nullScraper);

        //
        Assert.fail();
    }

    @Test
    public void Crawler_Successful_ValidateScraperIsAssigned()
    {
        // Arrange
        IScraper mockedScraper = Mockito.mock(IScraper.class);

        // Act -- no exception should be thrown
        Crawler crawler = new Crawler(mockedScraper);

        // Assert
        Assert.assertNotNull(crawler.getScraper());
    }

    @Test
    public void StoreCrawlRecord_ReturnsTrue_WhenAValidActionObjectIsPassed()
    {
        // Arrange
        Action action = new Action(1,"100",1,1,1);
        action.Id = 1;
        action.PagesExplored = 1;
        action.SearchDepth = 1;
        action.TimeElapsed = "100";
        action.UniquePagesFound = 1;
        IScraper mockedScrapper = Mockito.mock(IScraper.class);
        Crawler crawler = new Crawler(mockedScrapper);

        // Act
        Boolean result = crawler.StoreCrawRecord(action);

        // Assert
        Assert.assertTrue(result);
    }

    @Test
    public void StoreCrawlRecord_ReturnsFalse_WhenNullActionObjectIsPassed()
    {
        // Arrange
        Action action = null;

        IScraper mockedScrapper = Mockito.mock(IScraper.class);
        Crawler crawler = new Crawler(mockedScrapper);

        // Act
        Boolean result = crawler.StoreCrawRecord(action);

        // Assert
        Assert.assertFalse(result);
    }

    @Test
    public void StoreCrawlRecord_ReturnsFalse_WhenActionDoesNotHaveAnId()
    {
        // Arrange
        Action action = new Action(1,"100", 1,1,1);
        action.PagesExplored = 1;
        action.SearchDepth = 1;
        action.TimeElapsed = "100";
        action.UniquePagesFound = 1;
        IScraper mockedScrapper = Mockito.mock(IScraper.class);
        Crawler crawler = new Crawler(mockedScrapper);

        // Act
        Boolean result = crawler.StoreCrawRecord(action);

        // Assert
        Assert.assertFalse(result);
    }

    @Test
    public void StoreCrawlRecord_ReturnsTrue_ValidatePrivateFieldHasBeenUpdated()
    {
        // Arrange
        Action action = new Action(1,"100", 1,1,1);
        action.Id = 1;
        action.PagesExplored = 1;
        action.SearchDepth = 1;
        action.TimeElapsed = "100";
        action.UniquePagesFound = 1;
        IScraper mockedScrapper = Mockito.mock(IScraper.class);
        Crawler crawler = new Crawler(mockedScrapper);

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

    @Test
    public void CrawlWholeWebsite_ReturnsValidScrape_WhenValidUrlIsPassed()
    {
        // Arrange
        IScraper mockScrapper = Mockito.mock(IScraper.class);
        Crawler crawler = Mockito.mock(Crawler.class);
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

        Mockito.when(crawler.GetDocument(baseUrl)).thenReturn(document);
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
        Crawler crawler = new Crawler(mockedScrapper);
        String invalidUrl = "thisIsAnInvalidUrl";

        // Act
        crawler.CrawWholeWebsite(null);

        // Assert
        Assert.fail();
    }

    @Test
    public void CrawlWHoleWebsite_ThrowsIllegalArgumentException_WhenNullUrlIsPassed()
    {
        // Arrange
        IScraper mockedScrapper = Mockito.mock(IScraper.class);
        Crawler crawler = new Crawler(mockedScrapper);
        String invalidUrl = null;

        // Act
        crawler.CrawWholeWebsite(null);

        // Assert
        Assert.fail();
    }

    @Test
    public void GetLastAction_ReturnsLastAction_Successful()
    {
        IScraper mockScrapper = Mockito.mock(IScraper.class);
        Crawler crawler = Mockito.mock(Crawler.class);
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

        Mockito.when(crawler.GetDocument(baseUrl)).thenReturn(document);
        Mockito.when(mockScrapper.GetScrape(document)).thenReturn(expectedScrape);

        // Act
        crawler.CrawWholeWebsite(baseUrl); // this should make a scrape and
                                          // save an action in the lastActionobject
        Action lastScrape = crawler.GetLastAction();

        // Assert
        Assert.assertEquals(expectedScrape, lastScrape);
    }

    @Test
    public void GetLastAction_ReturnsNull_WhenLastActionIsNotInitialized()
    {
        // Arrange
        IScraper mockScraper = new Mockito().mock(IScraper.class);
        Crawler crawler = new Crawler(mockScraper);

        // Act
        Action lastScrape = crawler.GetLastAction();

        // Assert
        Assert.assertNull(lastScrape);
    }

    @Test
    public void FindItem_ReturnsFoundItemSuccessful()
    {
        // Arrange
        String baseUrl = "http://website.com";
        Document document = new Document(baseUrl);
        IScraper mockScraper = Mockito.mock(IScraper.class);
        Crawler crawler = Mockito.mock(Crawler.class);
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

        Mockito.when(crawler.GetDocument(baseUrl)).thenReturn(document);
        Mockito.when(mockScraper.FindItem(document, itemType, itemName)).thenReturn(bookToReturn);

        // Act
        Item resultingItem = crawler.FindItem(baseUrl, itemType, itemName);

        // Assert
        Assert.assertTrue(resultingItem.getClass() == Book.class);
        Assert.assertEquals(bookToReturn, resultingItem);
    }

    @Test(expected = IllegalArgumentException.class)
    public void FindItem_ThrowsIllegalArgumentException_WhenBaseUrlIsInvalid()
    {
        // Arrange
        String baseUrl = "InvalidUrl";
        IScraper mockScraper = Mockito.mock(IScraper.class);
        Crawler crawler = Mockito.mock(Crawler.class);
        String itemType = "book";
        String itemName = "bookBook";

        // Act
        crawler.FindItem(baseUrl, itemType, itemName);

        // Assert
        Assert.fail();
    }

    @Test (expected = IllegalArgumentException.class)
    public void FindItem_ThrowsIllegalArgumentException_WhenBaseUrlIsNull()
    {
        // Arrange
        String baseUrl = null;
        IScraper mockScraper = Mockito.mock(IScraper.class);
        Crawler crawler = Mockito.mock(Crawler.class);
        String itemType = "book";
        String itemName = "bookBook";

        // Act
        crawler.FindItem(baseUrl, itemType, itemName);

        // Assert
        Assert.fail();
    }

    @Test (expected = IllegalArgumentException.class)
    public void FindItem_ThrowsIllegalArgumentException_WhenItemTypeIsNotValid()
    {
        // Arrange
        String baseUrl = "http://website.com";
        IScraper mockScraper = Mockito.mock(IScraper.class);
        Crawler crawler = Mockito.mock(Crawler.class);
        String itemType = "Car"; // parameter type is invalid
                                // suported are book, movie, music
        String itemName = "bookBook";

        // Act
        crawler.FindItem(baseUrl, itemType, itemName);

        // Assert
        Assert.fail();
    }

    @Test (expected = IllegalArgumentException.class)
    public void FindItem_ThrowsIllegalArgumentException_WhenItemTypeIsNull()
    {
        // Arrange
        String baseUrl = "http://website.com";
        IScraper mockScraper = Mockito.mock(IScraper.class);
        Crawler crawler = Mockito.mock(Crawler.class);
        String itemType = null;
        String itemName = "bookBook";

        // Act
        crawler.FindItem(baseUrl, itemType, itemName);

        // Assert
        Assert.fail();
    }

    @Test
    public void FindItem_ThrowsIllegalArgumentException_WhenItemValueIsEmpty()
    {
        // Arrange
        String baseUrl = "http://website.com";
        IScraper mockScraper = Mockito.mock(IScraper.class);
        Crawler crawler = Mockito.mock(Crawler.class);
        String itemType = "book";
        String itemName = "";

        // Act
        crawler.FindItem(baseUrl, itemType, itemName);

        // Assert
        Assert.fail();
    }

    @Test (expected = IllegalArgumentException.class)
    public void FindItem_ThrowsIllegalArgumentException_WhenItemValueIsNull()
    {
        // Arrange
        String baseUrl = "http://website.com";
        IScraper mockScraper = Mockito.mock(IScraper.class);
        Crawler crawler = Mockito.mock(Crawler.class);
        String itemType = "book";
        String itemName = null;

        // Act
        crawler.FindItem(baseUrl, itemType, itemName);

        // Assert
        Assert.fail();
    }

    @Test
    public void CrawlWebsite_ReturnsScrape_Successful()
    {
        // Arrange
        IScraper mockScraper = Mockito.mock(IScraper.class);
        Crawler crawler = Mockito.mock(Crawler.class);
        String baseUrl = "http://website.com";
        Document document = new Document(baseUrl);
//        Integer id, String timeElapsed, Integer pagesExplored, Integer uniquePagesFound, Integer searchDepth
        Action action = new Action(1, "100", 1,1,1);

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
        newScrape.Music.add(new Music("Song", 1995, "mp3", "Group" ));

        Mockito.when(crawler.GetDocument(baseUrl)).thenReturn(document);
        Mockito.when(mockScraper.GetScrape(document)).thenReturn(newScrape);

        // Act
        Scrape resultingScrape = crawler.CrawWebsite(baseUrl, initialScrape, action);

        // Assert
        Assert.assertFalse(resultingScrape.Movies.isEmpty());
        Assert.assertFalse(resultingScrape.Music.isEmpty());
        Assert.assertEquals("John Smith", resultingScrape.Movies.get(0).Director);
        Assert.assertEquals("Group" ,resultingScrape.Music.get(0).Artist);
        Assert.assertTrue(crawler.GetLastAction().PagesExplored == 2);
        Assert.assertTrue(crawler.GetLastAction().UniquePagesFound == 2);
        Assert.assertTrue(crawler.GetLastAction().Id == 1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void CrawlWebsite_ThrowsIllegalArgumentException_WhenBaseUrlIsNull()
    {
        // Arrange
        IScraper mockScraper = Mockito.mock(IScraper.class);
        Crawler crawler = Mockito.mock(Crawler.class);
        String baseUrl = null;

        Action action = new Action(1, "100", 1,1,1);

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

        // Assert
        Assert.fail();
    }

    @Test (expected = IllegalArgumentException.class)
    public void CrawlWebsite_ThrowsIllegalArgumentException_WhenBaseUrlIsNotValid()
    {
        // Arrange
        IScraper mockScraper = Mockito.mock(IScraper.class);
        Crawler crawler = Mockito.mock(Crawler.class);
        String baseUrl = "not a valid URL";

        Action action = new Action(1, "100", 1,1,1);

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

        // Assert
        Assert.fail();
    }

    @Test (expected = IllegalArgumentException.class)
    public void CrawlWebsite_ThrowsIllegalArgumentException_WhenCurrentScrapeIsNull()
    {
        // Arrange
        IScraper mockScraper = Mockito.mock(IScraper.class);
        Crawler crawler = Mockito.mock(Crawler.class);
        String baseUrl = "not a valid URL";

        Action action = new Action(1, "100", 1,1,1);

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

        // Assert
        Assert.fail();
    }

    @Test
    public void CrawlWebsite_ThrowsIllegalArgumentException_WhenCurrentActionIsNull()
    {
        // Arrange
        IScraper mockScraper = Mockito.mock(IScraper.class);
        Crawler crawler = Mockito.mock(Crawler.class);
        String baseUrl = "not a valid URL";

        Action action = new Action(1, "100", 1,1,1);

        // Act
        crawler.CrawWebsite(baseUrl, null, action);

        // Assert
        Assert.fail();
    }

    @Test
    public void CrawlWebsite_DoesNotUpdateScrape_WhenThereIsNoNewDataOnPageToScrape()
    {
        // Arrange
        IScraper mockScraper = Mockito.mock(IScraper.class);
        Crawler crawler = Mockito.mock(Crawler.class);
        String baseUrl = "http://website.com";
        Document document = new Document(baseUrl);
        Action action = new Action(1, "100", 1,1,1);

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


        Mockito.when(crawler.GetDocument(baseUrl)).thenReturn(document);
        Mockito.when(mockScraper.GetScrape(document)).thenReturn(newScrape);

        // Act
        Scrape resultingScrape = crawler.CrawWebsite(baseUrl, initialScrape, action);

        // Assert
        Assert.assertFalse(resultingScrape.Movies.isEmpty());
        Assert.assertTrue(resultingScrape.Music.isEmpty()); // no music was added this should be empty
        Assert.assertEquals("John Smith", resultingScrape.Movies.get(0).Director);
        Assert.assertTrue(crawler.GetLastAction().PagesExplored == 2);
        Assert.assertTrue(crawler.GetLastAction().UniquePagesFound == 1);
        Assert.assertTrue(crawler.GetLastAction().Id == 1);
    }

    @Test
    public void CrawlWebsite_Successful_UntilSpecifiedDept()
    {
        // Arrange
        IScraper mockScraper = Mockito.mock(IScraper.class);
        Crawler crawler = Mockito.mock(Crawler.class);
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
        secondScrape.Music.add(new Music("Song", 1995, "mp3", "Group" ));
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

        Mockito.when(crawler.GetDocument(baseUrl)).thenReturn(document);
        Mockito.when(crawler.GetDocument(secondbaseUrl)).thenReturn(secondDocument);
        Mockito.when(mockScraper.GetScrape(document)).thenReturn(secondScrape);
        Mockito.when(mockScraper.GetScrape(secondDocument)).thenReturn(thirdScrape);

        // Act
        Scrape resultingScrape = crawler.CrawWholeWebsite(baseUrl);

        // Assert
        Assert.assertFalse(resultingScrape.Movies.isEmpty());
        Assert.assertFalse(resultingScrape.Music.isEmpty());
        Assert.assertEquals("John Smith", resultingScrape.Movies.get(0).Director);
        Assert.assertEquals("Group" ,resultingScrape.Music.get(0).Artist);
        Assert.assertTrue(crawler.GetLastAction().PagesExplored == 2);
        Assert.assertTrue(crawler.GetLastAction().UniquePagesFound == 2);
        Assert.assertTrue(crawler.GetLastAction().Id == 1);
    }
}

