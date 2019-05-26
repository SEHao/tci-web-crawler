class CrawlerTest extends GroovyTestCase {


    public void Crawler_Successful_WhenValidIScrapperIsPassed(){}

    public void Crawler_ThrowsIlegalArgumentException_WhenNullIScraperIsPassed(){}

    public void Crawler_Successsfull_ValidateScraperIsAssigned(){}

    public void StoreCrawRecord_ReturnsTrue_WhenAValidActionObjectIsPassed(){}

    public void StoreCrawRecord_ReturnsFalse_WhenNullActionObjectIsPassed(){}

    public void StoreCrawRecord_ReturnsFalse_WhenActionDoesNotHaveAnId(){}

    public void StoreCrawRecord_ReturnsTrue_ValidatePrivateFieldHasBeenUpdated(){}

    public void CrawWholeWebsite_ReturnsValidScrape_WhenValidUrlIsPassed(){}

    public void CrawWholeWebsite_ThrowsIllegalArgumentException_WhenInvalidUrlIsPassed(){}

    public void CrawWHoleWebsite_ThrowsIllegalArgumentException_WhenNullUrlIsPassed(){}

    public void GetLastAction_ReturnsLastAction_Successful(){}

    public void GetLastAction_ReturnsNull_WhenLastActionIsNotInitialized(){}

    public void FindItem_ReturnsFoundItemSuccessful(){}

    public void FindItem_ThrowsIllegalArgumentException_WhenBaseUrlIsInvalid(){}

    public void FindItem_ThrowsIllegalArgumentException_WhenBaseUrlIsNull(){}

    public void FindItem_ThrowsIllegalArgumentException_WhenItemTypeIsNotValid(){}

    public void FindItem_ThrowsIllegalArugmentException_WhenItemTypeIsNull(){}

    public void FindItem_ThrowsIllegalArgumentException_WhenItemValueIsEmpty(){}

    public void FindItem_ThrowsIllegalArgumentException_WhenItemValueIsNull(){}

    public void CrawWebsite_ReturnsScrape_Succesfull(){}

    public void CrawWebsite_ThrowsIllegalArgumentException_WhenBaseUrlIsNull(){}

    public void CrawWebsite_ThrowsIllegalArgumentException_WhenBaseUrlIsNotValid(){}

    public void CrawWebsite_ThrowsIllegalArugmentException_WhenCurrentScrapeIsNull(){}

    public void CrawWebsite_ThrowsIllegalArugmentException_WhenCurrentActionIsNull(){}

    public void CrawWebsite_DoesNotUpdateScrape_WhenThereIsNoNewDataOnPageToScrape(){}

    public void CrawWebsite_UpdatesDataInScrape_WhenThereIsNewDataInThePage(){}
}

