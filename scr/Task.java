
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Task {
    private int id;
    private String heading;
    private String description;
    private int priority;
    private Date deadline;
    private Status status;
    private Date dateOfCompletion;
    private DateFormat dateFormat;

//    public static int count;

    public Task(){ }

    public Task(Dictionary<String, String> values) {
        this.heading = values.get("heading");
        this.id = parseInt(values.get("id"));
        this.description = values.get("description");
        this.priority = parseInt(values.get("priority"));
        this.deadline = parseDate(values.get("deadline"));
//        this.currentStatus = values.get("status").isBlank()) ? Status.NEW : Status.valueOf(values.get("Status"));
//        this.dateOfCompletion = values.get("dateOfCompletion").isBlank() ? this.dateOfCompletion : parseDate(values.get("dateOfCompletion"));
        try {
            try {
                this.dateOfCompletion = parseDate(values.get("dateOfCompletion"));
            } catch (Exception e) {
                e.getMessage();
                System.out.println("Using null as value of Date of completion");
            }
            this.status = Status.valueOf(values.get("status").toUpperCase());
        } catch (Exception e){
            e.getMessage();
            System.out.println("Using NEW  as value of Status");
            this.status = Status.NEW;
        }
    }

    public void changeById(Dictionary<String, String> values) {
        this.heading = (values.get("heading").isEmpty()) ? this.heading : values.get("heading");
        this.description = (values.get("description").isEmpty()) ? this.description : values.get("description");
        this.priority = (values.get("priority").isEmpty()) ? this.priority : parseInt(values.get("priority"));
        this.deadline = (values.get("deadline").isEmpty()) ? this.deadline : parseDate(values.get("deadline"));
        this.status = (values.get("status").isEmpty()) ? this.status : Status.valueOf(values.get("Status"));
        this.dateOfCompletion = values.get("dateOfCompletion").isEmpty() ? this.dateOfCompletion : parseDate(values.get("dateOfCompletion"));
    }

    public void display(){
        /*if(this.dateOfCompletion == null && this.deadline == null) {
            String output = String.format("Task %d \n " +
                            "Heading: %s\n Description: %s\n Priority: %d\n Current Status: %s\n ",
                    this.id, this.heading, this.description, this.priority, this.currentStatus);
            System.out.println(output);
        }
        else if(this.dateOfCompletion != null ){
            String output = String.format("Task %d \n " + "Heading: %s\n Description: %s\n " +
                            "Priority: %d\n Current Status: %s\n Deadline: %s\n",
                    this.id, this.heading, this.description, this.priority, this.currentStatus,
                    dateFormat.format(this.deadline));
            System.out.println(output);
        }
        else if(this.deadline != null ){
            String output = String.format(new StringBuilder().append("Task %d \n ")
                            .append("Heading: %s\n Description: %s\n Priority: %d\n Current Status: %s\n ")
                            .append("Deadline: %s\n Date of Completion: %s\n").toString(),
                    this.id, this.heading, this.description, this.priority, this.currentStatus,
                    dateFormat.format(this.dateOfCompletion));
            System.out.println(output);
        }
        else {*/
            String output = String.format("Task %d \n Heading: %s\n Description: %s\n Priority: %d\n " +
                            "Current Status: %s\n Deadline: %s\n Date of Completion: %s\n",
                    this.id, this.heading, this.description, this.priority, this.status,
                    dateFormat.format(this.deadline), dateFormat.format(this.dateOfCompletion));
            System.out.println(output);
        //}
    }
    private Date parseDate(String strDate) {
        Date newDate = new Date();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try{
            newDate =  dateFormat.parse(strDate);
        }
        catch(Exception e){
            System.out.println("Incorrect date format. Date should be YYYY-MM-DD");
        }
            return newDate;
    }
    private int parseInt(String strId) {
        String[] ids = strId.split(" ");
        return Integer.parseInt(ids[ids.length-1]);
    }

    public int getId(){
        return this.id;
    }
    public String getHeading(){
        return this.heading;
    }
    public String getDescription(){
        return this.description;
    }
    public Status getStatus(){
        return this.status;
    }
    public int getPriority(){
        return this.priority;
    }
    public Date getDeadline(){
        return this.deadline;
    }
    public Date getDateOfCompletion(){
        return this.dateOfCompletion;
    }
    public void setDateOfCompletion(String newDateOfCompletion){
        this.dateOfCompletion = parseDate(newDateOfCompletion);
    }

}
