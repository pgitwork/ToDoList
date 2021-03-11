
import org.w3c.dom.*;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XMLDoc {
    DocumentBuilderFactory dbFactory;
    DocumentBuilder dBuilder;
    Document doc;

    public XMLDoc(){
        dbFactory = DocumentBuilderFactory.newInstance();
    }
    public void saveSession(List<Task> toDoLists, String filePath) {
        try{
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.newDocument();
            Dictionary<String, String> values;

            //root element
            Element rootElement = doc.createElement("ToDoList");
            doc.appendChild(rootElement);

            for(Task task :  toDoLists){
                //elements
                Element taskElement = doc.createElement("Task");
                Attr headingAttr = doc.createAttribute("Caption");
                headingAttr.setValue(task.getHeading());
                taskElement.setAttributeNode(headingAttr);
                Attr idAttr = doc.createAttribute("id");
                idAttr.setValue(String.valueOf(task.getId()));
                taskElement.setAttributeNode(idAttr);

                Element desc = doc.createElement("Description");
                desc.setTextContent(task.getDescription());
                taskElement.appendChild(desc);

                Element priority = doc.createElement("Priority");
                priority.setTextContent(String.valueOf(task.getPriority()));
                taskElement.appendChild(priority);

                Element status = doc.createElement("Status");
                status.setTextContent(String.valueOf(task.getStatus()).toLowerCase());
                taskElement.appendChild(status);

                Element deadline = doc.createElement("Deadline");
                deadline.setTextContent(String.valueOf(task.getDeadline()));
                taskElement.appendChild(deadline);

                Element dateOfComp = doc.createElement("DateOfCompletion");
                dateOfComp.setTextContent(String.valueOf(task.getDateOfCompletion()));
                taskElement.appendChild(dateOfComp);

                rootElement.appendChild(taskElement);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number","3");
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT,"yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source,result);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public List<Task> startSession(String filePath){
        List <Task> taskList = new ArrayList<>();
        try{
            dBuilder = dbFactory.newDocumentBuilder();
            //InputSource inSource = new InputSource(new FileInputStream(filePath));
            File f = new File(filePath);
            Document doc = dBuilder.parse(f); //dBuilder.parse(inSource);
            NodeList nodeList = doc.getElementsByTagName("ToDoList");
            for(int i = 0; i<nodeList.getLength();i++){
                Node nNode = nodeList.item(i);
                Dictionary<String, String> inputs = new Hashtable<>();
                if(nNode.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) nNode;
                    inputs.put("id", String.valueOf(element.getElementsByTagName("id")));
                    inputs.put("heading", String.valueOf(element.getElementsByTagName("Heading")));
                    inputs.put("description", String.valueOf(element.getElementsByTagName("Description")));
                    inputs.put("priority", String.valueOf(element.getElementsByTagName("Priority")));
                    inputs.put("status",  String.valueOf(element.getElementsByTagName("Status")));
                    inputs.put("deadline", String.valueOf(element.getElementsByTagName("Deadline")));
                    inputs.put("dateOfCompletion", String.valueOf(element.getElementsByTagName("Date Of Completion")));
                }
                Task newTask = new Task(inputs);
                taskList.add(newTask);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return taskList;
    }
}
