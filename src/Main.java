import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PeopleQueue peopleQueue = new PeopleQueue();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the command:");

        while (scanner.hasNext()) {
            String input = scanner.nextLine();

            if ("EXIT".equals(input)) {
                System.out.println("Exiting program...");
                return;
            }

            peopleQueue.processInput(input);
        }
    }
}
