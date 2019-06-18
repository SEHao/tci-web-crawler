package Controllers;

import Crawler.Crawler;
import Interfaces.ICrawler;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URL;

@Path("crawler")
public class CrawlerController {

    private ICrawler _crawler;

    public CrawlerController(ICrawler crawler) {
        _crawler = crawler;
    }

    public CrawlerController()
    {
        _crawler = new Crawler();
    }

    @GET
    @Path("scrape/{baseUrl}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response GetScrapesOfWholeWebsite(@PathParam("baseUrl") String baseUrl){
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

    @GET
    @Path("scrape/{baseUri}/{type}/{value}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response GetItem(
        @PathParam("baseUri") String baseUrl,
        @PathParam("type") String type,
        @PathParam("value") String searchValue
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

        var item = _crawler.FindItem(baseUrl, type, searchValue);
        if(item == null)
        {
            return Response.serverError().build();
        }

        return Response.ok(item).build();
    }

    @GET
    @Path("last-crawl")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response GetLastCrawlAction()
    {
        var action = _crawler.GetLastAction();
        if(action == null)
        {
            return Response.serverError().build();
        }

        return Response.ok(action).build();
    }
}
