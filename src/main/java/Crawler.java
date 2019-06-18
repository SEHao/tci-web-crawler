import Database.Models.Action;
import Interfaces.ICrawler;
import Interfaces.IDocumentRetriever;
import Interfaces.IScraper;
import Models.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.jar.JarEntry;


public class Crawler implements ICrawler {

    private IScraper scraper;

    private IDocumentRetriever documentRetriever;

    private Action lastScrape;

    private int totalNumberOfPagesVisited;

    private int numberOfUniquePagesVisited;

    private List<String> allVisitedPages;

    private List<String> uniqueVisitedPages;

    private Scrape latestVersionOfScrape;

    public Crawler(IScraper scrapper, IDocumentRetriever documentRetriever){

        if(scrapper == null){
            throw new IllegalArgumentException("Cannot create a crawler because scraper is null");
        }
        if(documentRetriever == null){
            throw  new IllegalArgumentException("Cannot create a crawler because document retriever is null");
        }

        this.scraper = scrapper;
        this.documentRetriever = documentRetriever;
        this.totalNumberOfPagesVisited = 0;
        this.numberOfUniquePagesVisited = 0;
        this.allVisitedPages = new ArrayList<>();
        this.uniqueVisitedPages = new ArrayList<>();
        this.latestVersionOfScrape =
                new Scrape("1", 0L, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public IDocumentRetriever getDocumentRetriever(){
        return  this.documentRetriever;
    }

    public IScraper getScraper(){
        return this.scraper;
    }

    public Scrape CrawWebsite(String baseUrl, Scrape currentScrape, Action currentAction){

        if(baseUrl == null){
            throw  new IllegalArgumentException("Cannot scrape page. Base url is null");
        }

        if(baseUrl.isEmpty()){
            throw  new IllegalArgumentException("Cannot scrape page. Base url is empty");
        }

        try {
            URL url = new URL(baseUrl);
        } catch (MalformedURLException e) {
            throw  new IllegalArgumentException("URL is not i a valid format");
        }

        if(currentAction == null){
            throw new  IllegalArgumentException("Cannot craw website. Provided action is null");
        }

        if(currentScrape == null){
            throw new IllegalArgumentException("Cannot craw website. Provided scrape is null");
        }

        numberOfUniquePagesVisited++;
        totalNumberOfPagesVisited++;
        uniqueVisitedPages.add(baseUrl);
        allVisitedPages.add(baseUrl);

        // Get document of base url
        Document document = documentRetriever.GetDocument(baseUrl);

        //Scrape document of base url
        Scrape newScrape = scraper.GetScrape(document);

        List<Music> musicToAdd = new ArrayList<>();
        List<Movie> moviesToAdd = new ArrayList<>();
        List<Book> booksToAdd = new ArrayList<>();

        // Check if there is new movies to add;
        for (Movie movie : newScrape.getMovies()){
            if(!currentScrape.getMovies().contains(movie)){
                moviesToAdd.add(movie);
            }
        }

        // Check if there is new Music to add
        for (Music music : newScrape.getMusic()){
            if(!currentScrape.getMusic().contains(music)){
                musicToAdd.add(music);
            }
        }

        // Check if there is new books to add
        for (Book book : newScrape.getBooks()){
            if(!currentScrape.getBooks().contains(book)){
                booksToAdd.add(book);
            }
        }

        // Add all old data to the new list of data
        musicToAdd.addAll(currentScrape.getMusic());
        moviesToAdd.addAll(currentScrape.getMovies());
        booksToAdd.addAll(currentScrape.getBooks());

        // Update the scrape with data from current page
        currentScrape.setMusic(musicToAdd);
        currentScrape.setMovies(moviesToAdd);
        currentScrape.setBooks(booksToAdd);
        currentScrape.setTimeStamp(newScrape.getTimeStamp());
        currentScrape.setId(newScrape.getId());

        latestVersionOfScrape = currentScrape;

        Elements links = document.select("a[href]");
        List<String> allLinksOnPage = new ArrayList<>();

        for(Element link: links){
            allLinksOnPage.add(link.attr("abs:href"));
        }

        // add links to number of all pages visited
        for(String link: allLinksOnPage){
            allVisitedPages.add(link);
            totalNumberOfPagesVisited++;
        }

        //remove duplicates from list by adding them to a hashSet
        HashSet<String> hashSetLinks = new HashSet<>(allLinksOnPage);
        List<String> pagesToRemove = new ArrayList<>();

        // Get items from hashSet that have already been visited.
        for(String link : hashSetLinks){
            if(uniqueVisitedPages.contains(link)){
                pagesToRemove.add(link);
            }
        }

        // Remove items from hash set that have already been visited
        for(String page : pagesToRemove){
            hashSetLinks.remove(page);
        }

        for(String link : hashSetLinks){
            CrawWebsite(link, currentScrape, currentAction);
        }

        return currentScrape;
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
    public Scrape CrawWholeWebsite(String baseUrl)
    {
        // CREATE EMPTY ACTION
        if(baseUrl == null){
            throw  new IllegalArgumentException("Can not craw website because the provided url is null");
        }

        if(baseUrl.isEmpty()){
            throw  new IllegalArgumentException("Can not craw website because the provided url is empty");
        }

        try {
            URL url = new URL(baseUrl);
        } catch (MalformedURLException e) {
            throw  new IllegalArgumentException("Cannot craw website because provided website is not valid");
        }

        numberOfUniquePagesVisited = 0;
        numberOfUniquePagesVisited = 0;
        this.allVisitedPages.clear();
        this.uniqueVisitedPages.clear();
        Action action = new Action(1, "0", 0,0,0);
        Scrape scrape = new Scrape("1", 0L, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        Long startTime = System.currentTimeMillis();

        Scrape finalScrape = CrawWebsite(baseUrl, scrape, action);
        Long endTime = System.currentTimeMillis();

        Long timeElapsed = endTime - startTime;

        action.PagesExplored = totalNumberOfPagesVisited;
        action.TimeElapsed = timeElapsed.toString();
        action.UniquePagesFound = numberOfUniquePagesVisited;


        StoreCrawRecord(action);
        return finalScrape;
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

        try {
            URL url = new URL(baseUrl);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Url was not in a valid format");
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

        // ADD current link to number of unique pages

        Item item = null;
        Document document = documentRetriever.GetDocument(baseUrl);
        if(document != null)
        item = scraper.FindItem(document, itemType, value);

        // ADd all those to the pages visited
        // Remove every link I visited from this list.
        Elements links = document.select("a[href]");

        if (!links.isEmpty()){
            for (Element link : links){
                item = FindItem(link.attr("href"), itemType, value);
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
