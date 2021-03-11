import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    public static void main(String[] args)
    {
        Commands commands = new Commands();
        Scanner in = new Scanner(System.in);
        String consoleInput;
        commands.startSession("newSession.xml");
        System.out.println("Type \"HELP\" to see documentation \n ");
        while(!commands.getCloseApp()) {
            System.out.print("App-> ");
            consoleInput = in.nextLine().toLowerCase();
            commands.parseCommands(consoleInput);
            commands.interpret();
        }
        //

        System.out.println("Saving session and exiting application");
        commands.saveSession("newSession.xml");
    }
}
