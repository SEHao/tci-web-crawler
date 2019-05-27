class CrawlerTest extends GroovyTestCase {

    @Test
    public void Crawler_Successful_WhenValidIScrapperIsPassed(){}

    @Test
    public void Crawler_ThrowsIlegalArgumentException_WhenNullIScraperIsPassed(){}

    @Test
    public void Crawler_Successsfull_ValidateScraperIsAssigned(){}

    @Test
    public void StoreCrawlRecord_ReturnsTrue_WhenAValidActionObjectIsPassed(){}

    @Test
    public void StoreCrawlRecord_ReturnsFalse_WhenNullActionObjectIsPassed(){}

    @Test
    public void StoreCrawlRecord_ReturnsFalse_WhenActionDoesNotHaveAnId(){}

    @Test
    public void StoreCrawlRecord_ReturnsTrue_ValidatePrivateFieldHasBeenUpdated(){}

    @Test
    public void CrawlWholeWebsite_ReturnsValidScrape_WhenValidUrlIsPassed(){}

    @Test
    public void CrawlWholeWebsite_ThrowsIllegalArgumentException_WhenInvalidUrlIsPassed(){}

    @Test
    public void CrawlWHoleWebsite_ThrowsIllegalArgumentException_WhenNullUrlIsPassed(){}

    @Test
    public void GetLastAction_ReturnsLastAction_Successful(){}

    @Test
    public void GetLastAction_ReturnsNull_WhenLastActionIsNotInitialized(){}

    @Test
    public void FindItem_ReturnsFoundItemSuccessful(){}

    @Test
    public void FindItem_ThrowsIllegalArgumentException_WhenBaseUrlIsInvalid(){}

    @Test
    public void FindItem_ThrowsIllegalArgumentException_WhenBaseUrlIsNull(){}

    @Test
    public void FindItem_ThrowsIllegalArgumentException_WhenItemTypeIsNotValid(){}

    @Test
    public void FindItem_ThrowsIllegalArugmentException_WhenItemTypeIsNull(){}

    @Test
    public void FindItem_ThrowsIllegalArgumentException_WhenItemValueIsEmpty(){}

    @Test
    public void FindItem_ThrowsIllegalArgumentException_WhenItemValueIsNull(){}

    @Test
    public void CrawlWebsite_ReturnsScrape_Succesfull(){}

    @Test
    public void CrawlWebsite_ThrowsIllegalArgumentException_WhenBaseUrlIsNull(){}

    @Test
    public void CrawlWebsite_ThrowsIllegalArgumentException_WhenBaseUrlIsNotValid(){}

    @Test
    public void CrawlWebsite_ThrowsIllegalArugmentException_WhenCurrentScrapeIsNull(){}

    @Test
    public void CrawlWebsite_ThrowsIllegalArugmentException_WhenCurrentActionIsNull(){}

    @Test
    public void CrawlWebsite_DoesNotUpdateScrape_WhenThereIsNoNewDataOnPageToScrape(){}

    @Test
    public void CrawlWebsite_UpdatesDataInScrape_WhenThereIsNewDataInThePage(){}
}

