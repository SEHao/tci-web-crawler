package Controllers;

import Crawler.Crawler;
import Interfaces.ICrawler;
import Models.Scrape;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.net.URL;
import java.net.http.HttpResponse;

public class CrawlerController {

    //private ICrawler _crawler;

    public CrawlerController() {
        //_crawler = new Crawler();
    }

    @GET
    @Path("scrape/{baseUrl}")
    public Response GetScrapesOfWholeWebsite(@PathParam("baseUrl") String baseUrl, ICrawler _crawler){
        if(baseUrl == null || baseUrl.isEmpty())
        {
            return Response.status(400).build();
        }

        try {
            new URL(baseUrl);
        } catch (Exception e) {
            return Response.status(400).build();
        }

        var scrape = _crawler.CrawWholeWebsite(baseUrl);
        if(scrape == null)
        {
            return Response.serverError().build();
        }

        if(scrape.getBooks() == null || scrape.getMovies() == null || scrape.getMusic() == null)
        {
            return Response.serverError().build();
        }

        return Response.ok(scrape).build();
    }

    public Response GetItem(String baseUrl, String type, String searchValue){
        return null;
    }

    public Response GetLastCrawlAction() {
        return null;
    }
}
