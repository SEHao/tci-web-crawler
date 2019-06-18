package Interfaces;

import Models.Item;
import Models.Scrape;
import org.jsoup.nodes.Document;

public interface IScraper {

     Scrape GetScrape(Document docuement);

     Item FindItem(Document document, String type, String value);
}
