import Interfaces.IDocumentRetriever;
import Interfaces.IScraper;
import Models.*;
import org.junit.Assert;
import org.junit.Test;
import org.jsoup.nodes.Document;
import org.mockito.Mock;
import org.mockito.Mockito;


public class DocumentRetrieverTests {


    @Test (expected = IllegalArgumentException.class)
    public void GetDocument_ThrowsIllegalArugmentException_WhenPassedUrlIsNull(){

        // Arrange
        DocumentRetriever documentRetriever = new DocumentRetriever();

        // Act
        documentRetriever.GetDocument(null);

        // Assert
        Assert.fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void GetDocument_ThrowsIllegalArgumentException_WhenPassedUrlIsEmpty(){

        // Arrange
        DocumentRetriever documentRetriever = new DocumentRetriever();

        // Act
        documentRetriever.GetDocument("");

        // Assert
        Assert.fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void GetDocument_ThrowsIllegalArgumentException_WhenPassedUrlIsNotAValidUrl(){

        // Arrange
        DocumentRetriever documentRetriever = new DocumentRetriever();

        // Act
        documentRetriever.GetDocument("invalidUrl");

        // Assert
        Assert.fail();
    }
}
