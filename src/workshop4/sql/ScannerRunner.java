package workshop4.sql;

import workshop4.sql.services.CommandService;
import workshop4.sql.services.DatabaseRunner;

import java.util.Scanner;

public class ScannerRunner {
    public static void main(String[] args) {
        System.out.println("Write command:");
        Scanner console = new Scanner(System.in);

        CommandService commandService = new CommandService();
        DatabaseRunner databaseRunner = new DatabaseRunner();

        while (console.hasNext()) {
            String stringCommand = console.nextLine();
            commandService.buildCommand(stringCommand)
                    .ifPresent(databaseRunner::run);
        }
    }
}
