import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class XMLDocRunner {
    private SAXParser createSaxParser(){
        SAXParser saxParser = null;

        try{
            SAXParserFactory factory = SAXParserFactory.newInstance();
            saxParser = factory.newSAXParser();
            return saxParser;
        } catch (ParserConfigurationException | SAXException e){
            e.getMessage();
        }
        return saxParser;
    }
    public List<Task> parseTasks(String filePath) {
        CustomHandler handler = new CustomHandler();
//        String filePath = "session.xml";
        File xmlDoc = Paths.get(filePath).toFile();

        try{
            SAXParser parser = createSaxParser();
            parser.parse(xmlDoc, handler);
        } catch (SAXException | IOException e){
            e.getMessage();
        }
        return handler.getTasks();
    }
}
