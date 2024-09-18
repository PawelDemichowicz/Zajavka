import java.util.*;

public class PeopleQueue {
    private final HashMap<String, Integer> peopleNameCounter = new HashMap<>();
    private final Deque<Person> peopleQueue = new LinkedList<>();

    void processInput(String input) {
        if (input.contains("ADD PERSON")) {
            processAddPerson(input);
        } else if ("PROCESS".equals(input)) {
            processMovePerson(input);
        } else if (input.contains("LEAVE PERSON")) {
            processLeavePerson(input);
        } else {
            System.out.println("I don't understand. Please use correct command.\n");
        }
    }

    private void processAddPerson(String input) {
        String personInput = extractPersonInput(input);
        String[] personData = personInput.split("[_,]");
        boolean isVip = false;

        if (isValidPersonData(personData)) {
            printInvalidCommandMessage(input);
            return;
        }

        if (personData.length > 2) {
            if ("VIP".equals(personData[2])) {
                isVip = true;
            } else {
                printInvalidCommandMessage(input);
                return;
            }
        }

        Person person = createPerson(personInput, personData, isVip);

        if (isVip) {
            peopleQueue.addFirst(person);
        } else {
            peopleQueue.addLast(person);
        }

        printPersonAddedMessage(input, person);
    }

    private void processMovePerson(String input) {
        if (peopleQueue.isEmpty()) {
            System.out.printf("Queue is already empty%n");
            System.out.printf("Please provide another command%n%n");
            return;
        }
        System.out.printf("%s%n", input);
        System.out.printf("Processing queue: %s arrived at the store%n", peopleQueue.remove());
        System.out.printf("Queue: %s%n%n", peopleQueue);
    }

    private void processLeavePerson(String input) {
        String personInput = extractPersonInput(input);
        String[] personData = personInput.split("_");

        if (isValidPersonData(personData)) {
            printInvalidCommandMessage(input);
            return;
        }

        int personCounter = personData.length < 3 ? 1 : Integer.parseInt(personData[2]);
        Person personToRemove = new Person(personData[0], personData[1], personCounter);
        boolean remove = peopleQueue.remove(personToRemove);

        if (!remove) {
            System.out.printf("Are you sure you meant %s%n", personToRemove);
            System.out.printf("%s is not standing in the line %n%n", personToRemove);
            return;
        }

        System.out.printf("%s%n", input);
        System.out.printf("Leaving queue: %s%n", personInput);
        System.out.printf("Queue: %s%n%n", peopleQueue);
    }

    private String extractPersonInput(String input) {
        return input.substring(input.indexOf("(") + 1, input.indexOf(")"));
    }

    private boolean isValidPersonData(String[] personData) {
        return personData.length < 2;
    }

    private void printInvalidCommandMessage(String input) {
        System.out.printf("Provided command: %s%n", input);
        System.out.printf("Please provide correct command.%n%n");
    }

    private Person createPerson(String personInput, String[] personData, boolean isVip) {
        int personCounter = peopleNameCounter.getOrDefault(personInput, 0) + 1;
        peopleNameCounter.put(personInput, personCounter);
        return isVip ? new Person(personData[0], personData[1], personCounter, true) : new Person(personData[0], personData[1], personCounter);
    }

    private void printPersonAddedMessage(String input, Person person) {
        System.out.printf("%s%n", input);
        System.out.printf("%s came to the queue: %s%n", person, true);
        System.out.printf("Queue: %s%n%n", peopleQueue);
    }
}
