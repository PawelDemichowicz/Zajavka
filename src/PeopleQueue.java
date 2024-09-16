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
        String personInput = input.substring(input.indexOf("(") + 1, input.indexOf(")"));
        String[] personData = personInput.split("[_,]");
        boolean isVip = false;

        if (personData.length < 2) {
            System.out.printf("Provided command: %s%n", input);
            System.out.println("Wrong name or surname. Please provide correct command.\n");
            return;
        }

        if (personData.length > 2) {
            if ("VIP".equals(personData[2])) {
                isVip = true;
            } else {
                System.out.printf("Provided command: %s%n", input);
                System.out.println("I don't understand. Please use correct command.\n");
            }
        }

        int personCounter = peopleNameCounter.getOrDefault(personInput, 0) + 1;
        peopleNameCounter.put(personInput, personCounter);

        Person person;
        if (isVip) {
            person = new Person(personData[0], personData[1], personCounter, isVip);
            peopleQueue.addFirst(person);
        } else {
            person = new Person(personData[0], personData[1], personCounter);
            peopleQueue.addLast(person);
        }

        System.out.printf("%s%n", input);
        System.out.printf("%s came to the queue: %s%n", person, true);
        System.out.printf("Queue: %s%n%n", peopleQueue);
    }

    private void processMovePerson(String input) {
        if (peopleQueue.isEmpty()) {
            System.out.println("Queue is already empty");
            System.out.println("Please provide another command\n");
            return;
        }
        System.out.printf("%s%n", input);
        System.out.printf("Processing queue: %s arrived at the store%n", peopleQueue.remove());
        System.out.printf("Queue: %s%n%n", peopleQueue);
    }

    private void processLeavePerson(String input) {
        String personInput = input.substring(input.indexOf("(") + 1, input.indexOf(")"));
        String[] personData = personInput.split("_");
        int personCounter;

        if (personData.length < 2) {
            System.out.printf("Provided command: %s%n", input);
            System.out.println("Wrong name or surname. Please provide correct command.\n");
            return;
        }

        if (personData.length < 3) {
            personCounter = 1;
        } else {
            personCounter = Integer.parseInt(personData[2]);
        }

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
}
