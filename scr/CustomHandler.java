
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class CustomHandler extends DefaultHandler {

    private List<Task> taskList = new ArrayList<>();
    private Dictionary<String,String> values = new Hashtable<>();

    private boolean bId;
    private boolean bDescription;
    private boolean bPriority;
    private boolean bDeadline;
    private boolean bStatus;
    private boolean bDateOfCompletion;

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException{
        if("Task".equals(qName)){

            values.put("id", String.valueOf(attributes.getValue("id")));
            values.put("heading", String.valueOf(attributes.getValue("caption")));
        }
        switch(qName.toLowerCase()){
            case "description":
                bDescription = true;
                break;
            case "priority":
                bPriority = true;
                break;
            case "deadline":
                bDeadline = true;
                break;
            case "status":
                bStatus = true;
                break;
            case "complete":
                bDateOfCompletion = true;
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException{
        if(bDescription){
            values.put("description", new String(ch, start, length));
            bDescription = false;
        }
        if(bPriority){
            values.put("priority", new String(ch, start, length));
            bPriority = false;
        }
        if(bDeadline){
            values.put("deadline", new String(ch, start, length));
            bDeadline = false;
        }
        if(bDateOfCompletion){
            values.put("dateOfCompletion", new String(ch, start, length));
            bDateOfCompletion = false;
        }
        if(bStatus){
            values.put("status", new String(ch, start, length));
            bStatus = false;
        }
    }
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException{
        if("Task".equals(qName)){
            taskList.add(new Task(values));
        }
    }
    public List<Task> getTasks(){
        return taskList;
    }
}
