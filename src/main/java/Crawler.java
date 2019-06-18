import Database.Models.Action;
import Interfaces.ICrawler;
import Interfaces.IDocumentRetriever;
import Interfaces.IScraper;
import Models.Item;
import Models.Scrape;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;


public class Crawler implements ICrawler {

    private IScraper scraper;

    private IDocumentRetriever documentRetriever;

    private Action lastScrape;

    public Crawler(IScraper scrapper, IDocumentRetriever documentRetriever){

        if(scrapper == null){
            throw new IllegalArgumentException("Cannot create a crawler because scraper is null");
        }
        if(documentRetriever == null){
            throw  new IllegalArgumentException("Cannot create a crawler because document retriever is null");
        }

        this.scraper = scrapper;
        this.documentRetriever = documentRetriever;
    }

    public IDocumentRetriever getDocumentRetriever(){
        return  this.documentRetriever;
    }

    public IScraper getScraper(){
        return this.scraper;
    }

    public Scrape CrawWebsite(String baseUrl, Scrape currentScrape, Action currentAction){
        return null;
    }

    /**
     * Store information about a record in a database.
     * @param action Data about the craw action that has to be stored.
     * @return Returns true if the item has been successfully stored.
     * Returns false if the item cannot be stored.
     */
    public Boolean StoreCrawRecord(Action action){

        if(action == null){
            return false;
        }

        if(action.Id == null){
            return false;
        }

        this.lastScrape = action;
        return true;
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

    /**
     * Finds an item (Movie, Music, Book) from
     * @param baseUrl The base Url of the website that is to be crawed.
     * @param itemType The type of item that is to be found during the scrape (Book, Music, Movie)
     * @param value The name of the item that should be found.
     * @return
     */
    @Override
    public Item FindItem(String baseUrl, String itemType, String value) {

        if(baseUrl == null){
            throw new IllegalArgumentException();
        }
        if(baseUrl.isEmpty()){
            throw new IllegalArgumentException("Base url cannot be empty");
        }
        if(itemType == null){
            throw new IllegalArgumentException("Item type cannot be null");
        }
        if(itemType.isEmpty()){
            throw  new IllegalArgumentException("Item type cannot be empty");
        }
        if(!itemType.trim().toLowerCase().equals("book") &&
                !itemType.trim().toLowerCase().equals("movie") &&
                !itemType.trim().toLowerCase().equals("music")){
            throw new IllegalArgumentException("Unrecognized item type");
        }
        if(value == null){
            throw new IllegalArgumentException("Value cannot be null");
        }
        if(value.isEmpty()){
            throw new IllegalArgumentException("Value cannot be empty");
        }

        Item item = null;
        Document document = documentRetriever.GetDocument(baseUrl);
        if(document != null)
        item = scraper.FindItem(document, itemType, value);

        Elements links = document.select("a[href]");

        if (!links.isEmpty()){
            for (Element link : links){
                document = documentRetriever.GetDocument(link.attr("href"));
                if(document != null)
                item = scraper.FindItem(document, itemType, value);
            }
        }
        return item;
    }

    /**
     * Retrieves the most recently stored action from the database
     * @return Returns the last action recorded in the database.
     * Returns null if the database is empty.
     */
    @Override
    public Action GetLastAction() {
        return this.lastScrape;
    }
}
