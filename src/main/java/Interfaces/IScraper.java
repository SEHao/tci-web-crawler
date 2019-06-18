package Interfaces;

import Models.Item;
import org.jsoup.nodes.Document;
import Models.Scrape;

public interface IScraper {

     Scrape GetScrape(Document docuement);

     Item FindItem(Document document, String type, String value);
}
