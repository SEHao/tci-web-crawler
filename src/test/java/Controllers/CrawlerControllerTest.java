package Controllers;

import org.junit.Test;

import static org.junit.Assert.*;

public class CrawlerControllerTest {

    // region GetScrapeWholeWebsite() endpoint.

    @Test
    public void GetScrapeWholeWebsite_BadRequest_WhenBaseUrlIsNull(){

    }

    @Test
    public void GetScrapeWholeWebsite_BadRequest_WhenBaseUrlIsEmpty(){

    }

    @Test
    public void GetScrapeWholeWebsite_BadRequest_WhenPassedStringIsNotValidUrl(){

    }

    @Test
    public void GetScrapeWholeWebsite_NotFound_WhenBaseUrlISNotValidUri(){

    }

    @Test
    public void GetScrapeWholeWebsite_InternalServerError_WhenCrawlWholeWebsiteReturnsNull(){

    }

    @Test
    public void GetScrapeWholeWebsite_InternalServerError__WhenMoviesInAScrapeIsNull(){

    }

    @Test
    public void GetScrapeWholeWebsite_InternalServerError__WhenBooksInAScrapeIsNull(){

    }

    @Test
    public void GetScrapeWholeWebsite_InternalServerError__WhenMusicInAScrapeIsNull(){

    }

    @Test
    public void GetScrapeWholeWebsite_Successful(){

    }

    // endregion

    // region GetItem() endpoint.

    @Test
    public void GetItem_BadRequest_WhenBaseUrlIsNull(){

    }

    @Test
    public void GetItem_BadRequest_WhenBaseUrlIsEmpty(){

    }

    @Test
    public void GetItem_BadRequest_WhenTypeIsNull(){

    }

    @Test
    public void GetItem_BadRequest_WhenTypeIsEmpty(){

    }

    @Test
    public void GetItem_BadRequest_WhenSearchValueIsNull(){

    }

    @Test
    public void GetItem_BadRequest_WhenSearchValueISEmpty(){

    }

    @Test
    public void GetItem_InternalServerError_WhenFindItemReturnsNull(){

    }

    @Test
    public void GetItem_InternalServerError_WhenResultInAScrapeIsNull(){

    }

    @Test
    public void GetItem_Successful(){

    }

    // endregion.

    // region GetLastCrawlAction() endpoint.

    @Test
    public void GetLastCrawlAction_InternalServerError_WhenGetLastActionReturnsNull(){

    }

    @Test
    public void GetCrawlAction_Successful(){

    }

    // endregion
}