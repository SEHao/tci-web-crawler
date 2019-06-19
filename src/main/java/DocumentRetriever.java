import Interfaces.IDocumentRetriever;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class DocumentRetriever implements IDocumentRetriever {

    public DocumentRetriever(){

    }

    @Override
    public Document GetDocument(String baseUrl) {
        try{
            if (baseUrl == null){
                throw new
                        IllegalArgumentException("Could not create document because passed URL was null");
            }

            if (baseUrl.isEmpty()){
                throw new
                        IllegalArgumentException("Could not create document because passed URL was empty");
            }

            URL formatedUrl = new URL(baseUrl);

            Document document = Jsoup.connect(formatedUrl.toString()).timeout(6000).get();
            return document;
        }
        catch (MalformedURLException e) {
            throw new
                    IllegalArgumentException("Could not create document submited imput was not a valid URL");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
