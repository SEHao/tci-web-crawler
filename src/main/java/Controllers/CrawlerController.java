package Controllers;

import Crawler.Crawler;
import Interfaces.ICrawler;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.net.URL;

public class CrawlerController {

    //private ICrawler _crawler;

    public CrawlerController() {
        //_crawler = new Crawler();
    }

    @GET
    @Path("scrape/{baseUrl}")
    public Response GetScrapesOfWholeWebsite(@PathParam("baseUrl") String baseUrl, ICrawler crawler){
        if(baseUrl == null || baseUrl.isEmpty())
        {
            return Response.status(400).build();
        }

        try {
            new URL(baseUrl);
        } catch (Exception e) {
            return Response.status(400).build();
        }

        var scrape = crawler.CrawWholeWebsite(baseUrl);
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

    @GET
    @Path("scrape/{baseUri}/{type}/{value}")
    public Response GetItem(
        @PathParam("baseUri") String baseUrl,
        @PathParam("type") String type,
        @PathParam("value") String searchValue,
        ICrawler crawler
    )
    {
        if(baseUrl == null || baseUrl.isEmpty() || type == null || type.isEmpty() || searchValue == null || searchValue.isEmpty())
        {
            return Response.status(400).build();
        }

        try {
            new URL(baseUrl);
        } catch (Exception e) {
            return Response.status(400).build();
        }

        var item = crawler.FindItem(baseUrl, type, searchValue);
        if(item == null)
        {
            return Response.serverError().build();
        }

        return Response.ok(item).build();
    }

    @GET
    @Path("last-crawl")
    public Response GetLastCrawlAction(ICrawler crawler)
    {
        var action = crawler.GetLastAction();
        if(action == null)
        {
            return Response.serverError().build();
        }

        return Response.ok(action).build();
    }
}
