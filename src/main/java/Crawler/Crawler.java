package Crawler;

import Database.Models.Action;
import Interfaces.ICrawler;
import Interfaces.IScraper;
import Models.Item;
import Models.Scrape;
import org.jsoup.nodes.Document;

public class Crawler implements ICrawler {

    private IScraper scraper;

    private Action lastScrape;

    public Crawler(IScraper scrapper){
    }

    public Crawler(){}

    public IScraper getScraper(){
        return this.scraper;
    }

    private Scrape CrawWebsite(String baseUrl, Scrape currentScrape, Action currentAction){
        return null;
    }

    /**
     * Store information about a record in a database.
     * @param action Data about the craw action that has to be stored.
     * @return Returns true if the item has been successfully stored.
     * Returns false if the item cannot be stored.
     */
    public Boolean StoreCrawRecord(Action action){
        return null;
    }

    /**
     * Craws and scrapes every page derived from the base url that was send.
     * @param baseUrl The base Url of the website that is to be crawed.
     * @return The scrape object that is returned after the scrape is complete.
     * Null if the website can't be craawed.
     */
    @Override
    public Scrape CrawWholeWebsite(String baseUrl) {
        return null;
    }

    public Document GetDocument(String url)
    {
        return null;
    }

    /**
     * Finds an item (Movie, Music, Book) from
     * @param baseUrl The base Url of the website that is to be crawed.
     * @param itemType The type of item that is to be found during the scrape (Book, Music, Movie)
     * @param value The name of the item that should be found.
     * @return
     */
    @Override
    public Item FindItem(String baseUrl, String itemType, String value) {
        return null;
    }

    /**
     * Retrieves the most recently stored action from the database
     * @return Returns the last action recorded in the database.
     * Returns null if the database is empty.
     */
    @Override
    public Action GetLastAction() {
        return null;
    }
}
