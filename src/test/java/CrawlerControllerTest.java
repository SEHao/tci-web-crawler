import Controllers.CrawlerController;
import Database.Models.Action;
import Interfaces.ICrawler;
import Interfaces.IScraper;
import Models.*;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CrawlerControllerTest {

    // region GetScrapeWholeWebsite() endpoint.

    @Test
    public void GetScrapeWholeWebsite_BadRequest_WhenBaseUrlIsNull(){

        // Arrange
        ICrawler mockedCrawler = Mockito.mock(ICrawler.class);
        CrawlerController crawlerController = new CrawlerController(mockedCrawler);

        // Act
        Response response = crawlerController.GetScrapesOfWholeWebsite(null);

        // Assert
        Assert.assertEquals(400, response.getStatus());
    }

    @Test
    public void GetScrapeWholeWebsite_BadRequest_WhenBaseUrlIsEmpty(){

        // Arrange
        ICrawler mockedCrawler = Mockito.mock(ICrawler.class);
        CrawlerController crawlerController = new CrawlerController(mockedCrawler);

        // Act
        Response response = crawlerController.GetScrapesOfWholeWebsite("");

        // Assert
        Assert.assertEquals(400, response.getStatus());
    }

    @Test
    public void GetScrapeWholeWebsite_BadRequest_WhenPassedStringIsNotValidUrl(){

        // Arrange
        ICrawler mockedCrawler = Mockito.mock(ICrawler.class);
        CrawlerController crawlerController = new CrawlerController(mockedCrawler);

        // Act
        Response response = crawlerController.GetScrapesOfWholeWebsite("abcd");

        // Assert
        Assert.assertEquals(400, response.getStatus());
    }

    @Test
    public void GetScrapeWholeWebsite_InternalServerError_WhenCrawlWholeWebsiteReturnsNull(){
        // Arrange
        ICrawler mockedCrawler = Mockito.mock(ICrawler.class);
        CrawlerController crawlerController = new CrawlerController(mockedCrawler);

        // Act
        Mockito.when(mockedCrawler.CrawWholeWebsite("https://www.google.com/")).thenReturn(null);
        Response response = crawlerController.GetScrapesOfWholeWebsite("https://www.google.com/");

        // Assert
        Assert.assertEquals(500, response.getStatus());
    }

    @Test
    public void GetScrapeWholeWebsite_InternalServerError__WhenMoviesInAScrapeIsNull(){
        // Arrange
        ICrawler mockedCrawler = Mockito.mock(ICrawler.class);
        CrawlerController crawlerController = new CrawlerController(mockedCrawler);
        List<Movie> movies = null;
        List<Music> musics = new ArrayList<Music>();
        List<Book> books = new ArrayList<Book>();

        Scrape scrape = new Scrape("6", (long) 76467876, movies, musics, books);

        // Act
        Mockito.when(mockedCrawler.CrawWholeWebsite("https://www.google.com/")).thenReturn(scrape);
        Response response = crawlerController.GetScrapesOfWholeWebsite("https://www.google.com/");

        // Assert
        Assert.assertEquals(500, response.getStatus());
    }

    @Test
    public void GetScrapeWholeWebsite_InternalServerError__WhenBooksInAScrapeIsNull(){
        // Arrange
        ICrawler mockedCrawler = Mockito.mock(ICrawler.class);
        CrawlerController crawlerController = new CrawlerController(mockedCrawler);
        List<Movie> movies = new ArrayList<Movie>();
        List<Music> musics = new ArrayList<Music>();
        List<Book> books = null;

        Scrape scrape = new Scrape("6", (long) 76467876, movies, musics, books);

        // Act
        Mockito.when(mockedCrawler.CrawWholeWebsite("https://www.google.com/")).thenReturn(scrape);
        Response response = crawlerController.GetScrapesOfWholeWebsite("https://www.google.com/");

        // Assert
        Assert.assertEquals(500, response.getStatus());
    }

    @Test
    public void GetScrapeWholeWebsite_InternalServerError__WhenMusicInAScrapeIsNull(){
        // Arrange
        ICrawler mockedCrawler = Mockito.mock(ICrawler.class);
        CrawlerController crawlerController = new CrawlerController(mockedCrawler);
        List<Movie> movies = new ArrayList<Movie>();
        List<Music> musics = null;
        List<Book> books = new ArrayList<Book>();

        Scrape scrape = new Scrape("6", (long) 76467876, movies, musics, books);

        // Act
        Mockito.when(mockedCrawler.CrawWholeWebsite("https://www.google.com/")).thenReturn(scrape);
        Response response = crawlerController.GetScrapesOfWholeWebsite("https://www.google.com/");

        // Assert
        Assert.assertEquals(500, response.getStatus());
    }

    @Test
    public void GetScrapeWholeWebsite_Successful(){
        // Arrange
        ICrawler mockedCrawler = Mockito.mock(ICrawler.class);
        CrawlerController crawlerController = new CrawlerController(mockedCrawler);
        List<Movie> movies = new ArrayList<Movie>();
        List<Music> musics = new ArrayList<Music>();
        List<Book> books = new ArrayList<Book>();

        Scrape scrape = new Scrape("6", (long) 76467876, movies, musics, books);

        // Act
        Mockito.when(mockedCrawler.CrawWholeWebsite("https://www.google.com/")).thenReturn(scrape);
        Response response = crawlerController.GetScrapesOfWholeWebsite("https://www.google.com/");

        // Assert
        Assert.assertEquals(200, response.getStatus());
    }

    // endregion

    // region GetItem() endpoint.

    @Test
    public void GetItem_BadRequest_WhenBaseUrlIsNull(){
        // Arrange
        ICrawler mockedCrawler = Mockito.mock(ICrawler.class);
        CrawlerController crawlerController = new CrawlerController(mockedCrawler);

        // Act
        Response response = crawlerController.GetItem(null, "Movie", "Terminator 2");

        // Assert
        Assert.assertEquals(400, response.getStatus());
    }

    @Test
    public void GetItem_BadRequest_WhenBaseUrlIsEmpty(){
        // Arrange
        ICrawler mockedCrawler = Mockito.mock(ICrawler.class);
        CrawlerController crawlerController = new CrawlerController(mockedCrawler);

        // Act
        Response response = crawlerController.GetItem("", "Movie", "Terminator 2");

        // Assert
        Assert.assertEquals(400, response.getStatus());
    }

    @Test
    public void GetItem_BadRequest_WhenTypeIsNull(){
        // Arrange
        ICrawler mockedCrawler = Mockito.mock(ICrawler.class);
        CrawlerController crawlerController = new CrawlerController(mockedCrawler);

        // Act
        Response response = crawlerController.GetItem("https://www.google.com/", null, "Terminator 2");

        // Assert
        Assert.assertEquals(400, response.getStatus());
    }

    @Test
    public void GetItem_BadRequest_WhenTypeIsEmpty(){
        // Arrange
        ICrawler mockedCrawler = Mockito.mock(ICrawler.class);
        CrawlerController crawlerController = new CrawlerController(mockedCrawler);

        // Act
        Response response = crawlerController.GetItem("https://www.google.com/", "", "Terminator 2");

        // Assert
        Assert.assertEquals(400, response.getStatus());
    }

    @Test
    public void GetItem_BadRequest_WhenSearchValueIsNull(){
        // Arrange
        ICrawler mockedCrawler = Mockito.mock(ICrawler.class);
        CrawlerController crawlerController = new CrawlerController(mockedCrawler);

        // Act
        Response response = crawlerController.GetItem("https://www.google.com/", "Movie", null);

        // Assert
        Assert.assertEquals(400, response.getStatus());
    }

    @Test
    public void GetItem_BadRequest_WhenSearchValueISEmpty(){
        // Arrange
        ICrawler mockedCrawler = Mockito.mock(ICrawler.class);
        CrawlerController crawlerController = new CrawlerController(mockedCrawler);

        // Act
        Response response = crawlerController.GetItem("https://www.google.com/", "Movie", "");

        // Assert
        Assert.assertEquals(400, response.getStatus());
    }

    @Test
    public void GetItem_InternalServerError_WhenFindItemReturnsNull(){
        // Arrange
        ICrawler mockedCrawler = Mockito.mock(ICrawler.class);
        CrawlerController crawlerController = new CrawlerController(mockedCrawler);

        // Act
        Mockito.when(mockedCrawler.FindItem("https://www.google.com/", "Movie", "Lord Of The Rings")).thenReturn(null);
        Response response = crawlerController.GetItem("https://www.google.com/", "Movie", "Lord Of The Rings");

        // Assert
        Assert.assertEquals(500, response.getStatus());
    }

    @Test
    public void GetItem_Successful(){
        // Arrange
        ICrawler mockedCrawler = Mockito.mock(ICrawler.class);
        CrawlerController crawlerController = new CrawlerController(mockedCrawler);
        Item item = new Item();

        // Act
        Mockito.when(mockedCrawler.FindItem("https://www.google.com/", "Movie", "Lord Of The Rings")).thenReturn(item);
        Response response = crawlerController.GetItem("https://www.google.com/", "Movie", "Lord Of The Rings");

        // Assert
        Assert.assertEquals(200, response.getStatus());
    }

    // endregion.

    // region GetLastCrawlAction() endpoint.

    @Test
    public void GetLastCrawlAction_InternalServerError_WhenGetLastActionReturnsNull(){
        // Arrange
        ICrawler mockedCrawler = Mockito.mock(ICrawler.class);
        CrawlerController crawlerController = new CrawlerController(mockedCrawler);

        // Act
        Mockito.when(mockedCrawler.GetLastAction()).thenReturn(null);
        Response response = crawlerController.GetLastCrawlAction();

        // Assert
        Assert.assertEquals(500, response.getStatus());
    }

    @Test
    public void GetCrawlAction_Successful(){
        // Arrange
        ICrawler mockedCrawler = Mockito.mock(ICrawler.class);
        CrawlerController crawlerController = new CrawlerController(mockedCrawler);
        Action action = new Action(12, (long) 76467876, 5, 2, 3);

        // Act
        Mockito.when(mockedCrawler.GetLastAction()).thenReturn(action);
        Response response = crawlerController.GetLastCrawlAction();

        // Assert
        Assert.assertEquals(200, response.getStatus());
    }

    // endregion
}
