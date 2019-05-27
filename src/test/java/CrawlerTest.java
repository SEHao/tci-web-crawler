class CrawlerTest extends GroovyTestCase {

    @Test
    public void Crawler_Successful_WhenValidIScrapperIsPassed(){}

    @Test
    public void Crawler_ThrowsIlegalArgumentException_WhenNullIScraperIsPassed(){}

    @Test
    public void Crawler_Successsfull_ValidateScraperIsAssigned(){}

    @Test
    public void StoreCrawRecord_ReturnsTrue_WhenAValidActionObjectIsPassed(){}

    @Test
    public void StoreCrawRecord_ReturnsFalse_WhenNullActionObjectIsPassed(){}

    @Test
    public void StoreCrawRecord_ReturnsFalse_WhenActionDoesNotHaveAnId(){}

    @Test
    public void StoreCrawRecord_ReturnsTrue_ValidatePrivateFieldHasBeenUpdated(){}

    @Test
    public void CrawWholeWebsite_ReturnsValidScrape_WhenValidUrlIsPassed(){}

    @Test
    public void CrawWholeWebsite_ThrowsIllegalArgumentException_WhenInvalidUrlIsPassed(){}

    @Test
    public void CrawWHoleWebsite_ThrowsIllegalArgumentException_WhenNullUrlIsPassed(){}

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
    public void CrawWebsite_ReturnsScrape_Succesfull(){}

    @Test
    public void CrawWebsite_ThrowsIllegalArgumentException_WhenBaseUrlIsNull(){}

    @Test
    public void CrawWebsite_ThrowsIllegalArgumentException_WhenBaseUrlIsNotValid(){}

    @Test
    public void CrawWebsite_ThrowsIllegalArugmentException_WhenCurrentScrapeIsNull(){}

    @Test
    public void CrawWebsite_ThrowsIllegalArugmentException_WhenCurrentActionIsNull(){}

    @Test
    public void CrawWebsite_DoesNotUpdateScrape_WhenThereIsNoNewDataOnPageToScrape(){}

    @Test
    public void CrawWebsite_UpdatesDataInScrape_WhenThereIsNewDataInThePage(){}
}

