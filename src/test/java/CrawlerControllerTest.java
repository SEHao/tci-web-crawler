import Controllers.CrawlerController;
import Interfaces.ICrawler;
import Interfaces.IScraper;
import Models.Book;
import Models.Movie;
import Models.Music;
import Models.Scrape;
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
        CrawlerController crawlerController = new CrawlerController();

        // Act
        Response response = crawlerController.GetScrapesOfWholeWebsite(null);

        // Assert
        Assert.assertEquals(400, response.getStatus());
    }

    @Test
    public void GetScrapeWholeWebsite_BadRequest_WhenBaseUrlIsEmpty(){

        // Arrange
        CrawlerController crawlerController = new CrawlerController();

        // Act
        Response response = crawlerController.GetScrapesOfWholeWebsite("");

        // Assert
        Assert.assertEquals(400, response.getStatus());
    }

    @Test
    public void GetScrapeWholeWebsite_BadRequest_WhenPassedStringIsNotValidUrl(){

        // Arrange
        CrawlerController crawlerController = new CrawlerController();

        // Act
        Response response = crawlerController.GetScrapesOfWholeWebsite("abcd");

        // Assert
        Assert.assertEquals(400, response.getStatus());
    }

    @Test
    public void GetScrapeWholeWebsite_NotFound_WhenBaseUrlISNotValidUri(){

        // Arrange
        CrawlerController crawlerController = new CrawlerController();

        // Act
        Response response = crawlerController.GetScrapesOfWholeWebsite("www.notAValidSite.com");

        // Assert
        Assert.assertEquals(401, response.getStatus());
    }

    @Test
    public void GetScrapeWholeWebsite_InternalServerError_WhenCrawlWholeWebsiteReturnsNull(){
        // Arrange
        CrawlerController crawlerController = new CrawlerController();
        ICrawler mockedCrawler = Mockito.mock(ICrawler.class);

        // Act
        Mockito.when(mockedCrawler.CrawWholeWebsite("localhost:8080/index.html")).thenReturn(null);
        Response response = crawlerController.GetScrapesOfWholeWebsite("localhost:8080/index.html");

        // Assert
        Assert.assertEquals(500, response.getStatus());
    }

    @Test
    public void GetScrapeWholeWebsite_InternalServerError__WhenMoviesInAScrapeIsNull(){
        // Arrange
        CrawlerController crawlerController = new CrawlerController();
        ICrawler mockedCrawler = Mockito.mock(ICrawler.class);
        List<Movie> movies = null;
        List<Music> musics = new ArrayList<Music>();
        List<Book> books = new ArrayList<Book>();

        Scrape scrape = new Scrape("6", (long) 76467876, movies, musics, books);

        // Act
        Mockito.when(mockedCrawler.CrawWholeWebsite("localhost:8080/index.html")).thenReturn(scrape);
        Response response = crawlerController.GetScrapesOfWholeWebsite("localhost:8080/index.html");

        // Assert
        Assert.assertEquals(500, response);
    }

    @Test
    public void GetScrapeWholeWebsite_InternalServerError__WhenBooksInAScrapeIsNull(){
        // Arrange
        CrawlerController crawlerController = new CrawlerController();
        ICrawler mockedCrawler = Mockito.mock(ICrawler.class);
        List<Movie> movies = new ArrayList<Movie>();
        List<Music> musics = new ArrayList<Music>();
        List<Book> books = null;

        Scrape scrape = new Scrape("6", (long) 76467876, movies, musics, books);

        // Act
        Mockito.when(mockedCrawler.CrawWholeWebsite("localhost:8080/index.html")).thenReturn(scrape);
        Response response = crawlerController.GetScrapesOfWholeWebsite("localhost:8080/index.html");

        // Assert
        Assert.assertEquals(500, response);
    }

    @Test
    public void GetScrapeWholeWebsite_InternalServerError__WhenMusicInAScrapeIsNull(){
        // Arrange
        CrawlerController crawlerController = new CrawlerController();
        ICrawler mockedCrawler = Mockito.mock(ICrawler.class);
        List<Movie> movies = new ArrayList<Movie>();
        List<Music> musics = null;
        List<Book> books = new ArrayList<Book>();

        Scrape scrape = new Scrape("6", (long) 76467876, movies, musics, books);

        // Act
        Mockito.when(mockedCrawler.CrawWholeWebsite("localhost:8080/index.html")).thenReturn(scrape);
        Response response = crawlerController.GetScrapesOfWholeWebsite("localhost:8080/index.html");

        // Assert
        Assert.assertEquals(500, response);
    }

    @Test
    public void GetScrapeWholeWebsite_Successful(){
        // Arrange
        CrawlerController crawlerController = new CrawlerController();

        // Act
        Response response = crawlerController.GetScrapesOfWholeWebsite("localhost:8080/index.html");

        // Assert
        Assert.assertEquals(200, response.getStatus());
    }

    // endregion

    // region GetItem() endpoint.

    @Test
    public void GetItem_BadRequest_WhenBaseUrlIsNull(){
        // Arrange
        CrawlerController crawlerController = new CrawlerController();

        // Act
        Response response = crawlerController.GetItem(null, "Movie", "Terminator 2");

        // Assert
        Assert.assertEquals(400, response.getStatus());
    }

    @Test
    public void GetItem_BadRequest_WhenBaseUrlIsEmpty(){
        // Arrange
        CrawlerController crawlerController = new CrawlerController();

        // Act
        Response response = crawlerController.GetItem("", "Movie", "Terminator 2");

        // Assert
        Assert.assertEquals(400, response.getStatus());
    }

    @Test
    public void GetItem_BadRequest_WhenTypeIsNull(){
        // Arrange
        CrawlerController crawlerController = new CrawlerController();

        // Act
        Response response = crawlerController.GetItem("localhost:8080", null, "Terminator 2");

        // Assert
        Assert.assertEquals(400, response.getStatus());
    }

    @Test
    public void GetItem_BadRequest_WhenTypeIsEmpty(){
        // Arrange
        CrawlerController crawlerController = new CrawlerController();

        // Act
        Response response = crawlerController.GetItem("localhost:8080", "", "Terminator 2");

        // Assert
        Assert.assertEquals(400, response.getStatus());
    }

    @Test
    public void GetItem_BadRequest_WhenSearchValueIsNull(){
        // Arrange
        CrawlerController crawlerController = new CrawlerController();

        // Act
        Response response = crawlerController.GetItem("localhost:8080", "Movie", null);

        // Assert
        Assert.assertEquals(400, response.getStatus());
    }

    @Test
    public void GetItem_BadRequest_WhenSearchValueISEmpty(){
        // Arrange
        CrawlerController crawlerController = new CrawlerController();

        // Act
        Response response = crawlerController.GetItem("localhost:8080", "Movie", "");

        // Assert
        Assert.assertEquals(400, response.getStatus());
    }

    @Test
    public void GetItem_InternalServerError_WhenFindItemReturnsNull(){
        // Arrange
        CrawlerController crawlerController = new CrawlerController();
        ICrawler mockedCrawler = Mockito.mock(ICrawler.class);

        // Act
        Mockito.when(mockedCrawler.FindItem("localhost:8080", "Movie", "Lord Of The Rings")).thenReturn(null);
        Response response = crawlerController.GetItem("localhost:8080", "Movie", null);

        // Assert
        Assert.assertEquals(500, response.getStatus());
    }

    @Test
    public void GetItem_Successful(){
        // Arrange
        CrawlerController crawlerController = new CrawlerController();

        // Act
        Response response = crawlerController.GetItem("localhost:8080", "Movie", "Lord Of The Rings");

        // Assert
        Assert.assertEquals(200, response.getStatus());
    }

    // endregion.

    // region GetLastCrawlAction() endpoint.

    @Test
    public void GetLastCrawlAction_InternalServerError_WhenGetLastActionReturnsNull(){
        // Arrange
        CrawlerController crawlerController = new CrawlerController();
        ICrawler mockedCrawler = Mockito.mock(ICrawler.class);

        // Act
        Mockito.when(mockedCrawler.GetLastAction()).thenReturn(null);
        Response response = crawlerController.GetLastCrawlAction();

        // Assert
        Assert.assertEquals(500, response.getStatus());
    }

    @Test
    public void GetCrawlAction_Successful(){
        // Arrange
        CrawlerController crawlerController = new CrawlerController();

        // Act
        Response response = crawlerController.GetLastCrawlAction();

        // Assert
        Assert.assertEquals(200, response.getStatus());
    }

    // endregion
}
