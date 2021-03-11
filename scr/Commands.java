import java.util.*;

public class Commands {
    // keywords = {"help", "list", "edit", "remove", "complete", "exit"}
    //options = {"-s"}
    //actions = {"new", "done",}

    private List<String> tokens;
    private List<Task> taskList;
    private XMLDoc doc;

    private boolean closeApp;
    public boolean getCloseApp(){
        return this.closeApp;
    }

    public Commands() {
        tokens = new ArrayList<>();
        taskList = new ArrayList<>();
        closeApp = false;
        doc = new XMLDoc();
    }
    public void parseCommands(String arguments){
        String[] args = arguments.toLowerCase().split(" ");
        tokens.addAll(Arrays.asList(args));
    }
    public void interpret(){
        switch(tokens.get(0)) {
            case "exit":
                this.closeApp = true;
                break;
            case "help":
                showHelpMsg();
                break;
            case "list":
                interpretList(1);
                break;
            case "edit":
                interpretEdit(1);
                break;
            case "remove":
                taskList.remove(Integer.parseInt(tokens.get(1)));
                break;
            case "new":
                interpretNew(1);
                break;
            case "complete":
                interpretComplete(1);
                break;
        }
        tokens.clear();
    }
    public void saveSession(String filePath){
//       doc.saveSession(taskList);
       doc.saveSession(taskList,filePath);
    }
    public void startSession(String filePath){
//        this.taskList = doc.startSession();
        XMLDocRunner runner = new XMLDocRunner();
        this.taskList = runner.parseTasks(filePath);
    }
    private void interpretList(int startIdx){
        if(startIdx ==  tokens.size()) {
            for(Task t : taskList) {
                t.display();
            }
            return;
        }
        if(tokens.get(startIdx).equals("-s")){
            interpret_S_Option(startIdx+1);
        }
    }
    private void interpretNew(int startIdx) {
        try {
            Dictionary<String, String> cmdLineInputs = parseCmdLineInputs(startIdx);
            if (verifyIds(cmdLineInputs.get("id"))){
                System.out.println("[INFO] ID already exists. Changing to " + (taskList.size()+1));
                cmdLineInputs.put("id",String.valueOf(taskList.size()+1));
            }
            Task newTask = new Task(cmdLineInputs);
            this.taskList.add(newTask);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    private void interpretEdit(int startIdx){
        try {
            Dictionary<String, String> edits = parseCmdLineInputs(startIdx);
            //get task with corresponding id and change the needed values
            Task t = getById(parseInt(edits.get("id")));
            t.changeById(edits);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    private void interpretComplete(int startIdx){
        try {
            Dictionary<String, String> edits = parseCmdLineInputs(startIdx);
            //get task with corresponding id and change the needed values
            Task t = getById(Integer.parseInt(edits.get("id")));
            t.setDateOfCompletion(edits.get("dateOfCompletion"));
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void interpret_S_Option(int startIdx){
        if(tokens.get(startIdx).equals("new")){
            //Show all new tasks
            for(Task t: taskList){
                if(t.getStatus().equals(Status.NEW))
                    t.display();
            }
        }
        else if(tokens.get(startIdx).equals("done")){
            for(Task t: taskList){
                if(t.getStatus().equals(Status.DONE))
                    t.display();
            }
        }
    }

    private void showHelpMsg(){
        String msg = new String();
        System.out.println("HELP MESSAGE");
        //todo show format of cmds to input plus all cmds and an example
    }
    private Dictionary<String,String> parseCmdLineInputs(int startIdx) {
        List<String> entry = tokens.subList(startIdx, tokens.size());
        String[] args = new String[entry.size()];
        for (int i = 0; i < args.length; i++) {
            args[i] = entry.get(i);
        }
        StringBuilder key = new StringBuilder();
        StringBuilder accumulator = new StringBuilder();
        Dictionary<String, String> inputDict = new Hashtable<>();
        int i = 0;
        while (i < args.length) {
            if (args[i].contains("--")) {
                String[] s = args[i].split("--");
                key.append(s[s.length - 1]);
                ++i;
                while (i < args.length && !args[i].contains("--")) {
                    accumulator.append(args[i]).append(" ");
                    ++i;
                }
                inputDict.put(key.toString(), accumulator.toString());
                key.delete(0, key.length());
                accumulator.delete(0, accumulator.length());
            }
        }
        return inputDict;
    }
    private boolean verifyIds(String id){
        for (Task t : taskList) {
            if (t.getId() == parseInt(id)) {
                return true;
            }
        }
        return false;
    }
    private Task getById(int id) throws Exception {
        for (Task t : taskList) {
            if (t.getId() == id) {
                return t;
            }
        }
        throw new Exception("Could not find Task with ID: "+String.valueOf(id));
    }
    private int parseInt(String strId) {
        String[] ids = strId.split(" ");
        return Integer.parseInt(ids[ids.length-1]);
    }
}

