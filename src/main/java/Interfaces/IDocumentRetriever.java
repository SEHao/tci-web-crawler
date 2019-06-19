package Interfaces;

import org.jsoup.nodes.Document;

public interface IDocumentRetriever {

    Document GetDocument(String baseUrl);
}
