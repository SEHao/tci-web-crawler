package Interfaces;

import Models.Item;
import Models.Scrape;
import org.w3c.dom.Document;

public interface IScraper {

     Scrape GetScrape(Document docuement);

     Item FindItem(Document document, String type, String value);
}
