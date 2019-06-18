import Interfaces.IScraper;
import Models.Item;
import Models.Scrape;
import org.jsoup.nodes.Document;

public class Scraper implements IScraper {

    /**
     * Scrapes a Document produced by Jsoup and returns a Scrape object.
     * @param document The Jsoup document that should be scraped.
     * @return Returns the scrape object after the craw has been completed.
     * Returns null if the document is empty.
     */
    @Override
    public Scrape GetScrape(Document document) {
        return null;
    }

    /**
     * Finds an item (Music, Movie or Book) in a Document produced by Jsop.
     * @param document The document that the item has to be found in.
     * @param type The type of the item that has to be found (Music, Movie, Book)
     * @param value The name of the item that has to be found.
     * @return Returns an Item if it can be found in the document. Returns null if the item can't be found.
     */
    @Override
    public Item FindItem(Document document, String type, String value) {
        return null;
    }
}
