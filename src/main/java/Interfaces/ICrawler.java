package Interfaces;

import Database.Models.Action;
import Models.Item;
import Models.Scrape;

public interface ICrawler {

    Scrape CrawWholeWebsite(String baseUrl);

    Item FindItem(String baseUrl, String itemType, String value);

    Action GetLastAction();
}
