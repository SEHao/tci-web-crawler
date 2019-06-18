import Database.Models.Action;
import Interfaces.IScraper;
import Models.Scrape;
import org.junit.Assert;
import org.junit.Test;
import org.jsoup.nodes.Document;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;


class CrawlerTest {

    @Test
    public void Crawler_Successful_WhenValidIScrapperIsPassed()
    {
        // Arrange
        IScraper mockedScraper = Mockito.mock(IScraper.class);

        // Act -- no exception should be thrown
        Crawler crawler = new Crawler(mockedScraper);

        // Assert - not needed
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
    public void FindItem_ReturnsFoundItemSuccessful(){}

    @Test
    public void FindItem_ThrowsIllegalArgumentException_WhenBaseUrlIsInvalid(){}

    @Test
    public void FindItem_ThrowsIllegalArgumentException_WhenBaseUrlIsNull(){}

    @Test
    public void FindItem_ThrowsIllegalArgumentException_WhenItemTypeIsNotValid(){}

    @Test
    public void FindItem_ThrowsIllegalArgumentException_WhenItemTypeIsNull(){}

    @Test
    public void FindItem_ThrowsIllegalArgumentException_WhenItemValueIsEmpty(){}

    @Test
    public void FindItem_ThrowsIllegalArgumentException_WhenItemValueIsNull(){}

    @Test
    public void CrawlWebsite_ReturnsScrape_Successful(){}

    @Test
    public void CrawlWebsite_ThrowsIllegalArgumentException_WhenBaseUrlIsNull(){}

    @Test
    public void CrawlWebsite_ThrowsIllegalArgumentException_WhenBaseUrlIsNotValid(){}

    @Test
    public void CrawlWebsite_ThrowsIllegalArgumentException_WhenCurrentScrapeIsNull(){}

    @Test
    public void CrawlWebsite_ThrowsIllegalArgumentException_WhenCurrentActionIsNull(){}

    @Test
    public void CrawlWebsite_DoesNotUpdateScrape_WhenThereIsNoNewDataOnPageToScrape(){}

    @Test
    public void CrawlWebsite_UpdatesDataInScrape_WhenThereIsNewDataInThePage(){}

    @Test
    public void CrawlWebsite_Successful_UntilSpecifiedDept(){}
}

