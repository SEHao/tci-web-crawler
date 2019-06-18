import Controllers.CrawlerController;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;

public class Publisher {
    public static void main(String[] args) {
        var port = 9095;
        var baseUri = UriBuilder.fromUri("http://localhost/").port(port).build();
        var resourceConfig = new ResourceConfig(CrawlerController.class);
        JdkHttpServerFactory.createHttpServer(baseUri, resourceConfig, true);
        System.out.println("Hosting service at http://localhost:" + port);
    }
}