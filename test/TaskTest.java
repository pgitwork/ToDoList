import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskTest {

    private static Task testTask;

    @BeforeAll
    static void init() {
        Dictionary<String, String> testValues = new Hashtable<>();
        testValues.put("id", "78");
        testValues.put("heading", "New heading");
        testValues.put("description", "New description");
        testValues.put("status", "in_progress");
        testValues.put("priority", "5");
        testValues.put("deadline", "2021-05-18");
        testValues.put("dateOfCompletion", "2021-05-18");
        testTask = new Task(testValues);
    }
    @Test
    void getId() {
        int id = testTask.getId();
        assertEquals(id,78);
    }

    @Test
    void getHeading() {
        String heading  = testTask.getHeading();
        assertEquals(heading, "New heading");
    }

    @Test
    void getDescription() {
        String description  = testTask.getDescription();
        assertEquals(description, "New description");
    }

    @Test
    void getStatus() {
        Status status = testTask.getStatus();
        assertEquals(status,Status.valueOf("in_progress"));
    }

    @Test
    void getPriority() {
        int priority = testTask.getPriority();
        assertEquals(priority,5);
    }

    @Test
    void getDeadline() {
        SimpleDateFormat dF = new SimpleDateFormat("yyyy-MM-dd");
        Date deadline = testTask.getDeadline();
        try {
            assertEquals(deadline, dF.parse("2021-05-18"));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void getDateOfCompletion() {
        SimpleDateFormat dF = new SimpleDateFormat("yyyy-MM-dd");
        Date dateOfComp = testTask.getDateOfCompletion();
        try {
            assertEquals(dateOfComp, dF.parse("2022 - 12 - 02"));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
