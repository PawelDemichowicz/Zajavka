package workshop1.collections;

import java.util.*;

public class PeopleQueue {
    private final HashMap<String, Integer> peopleNameCounter = new HashMap<>();
    private final Deque<Person> peopleQueue = new ArrayDeque<>();
    private final Deque<Person> peopleVipQueue = new ArrayDeque<>();

    void processInput(String input) {
        if (input.contains("ADD PERSON")) {
            processAddPerson(input);
        } else if ("PROCESS".equals(input)) {
            processEnterPerson(input);
        } else if (input.contains("LEAVE PERSON")) {
            processLeavePerson(input);
        } else {
            System.out.println("I don't understand. Please use correct command.\n");
        }
    }

    private void processAddPerson(String input) {
        String personInput = extractPersonInput(input);
        String[] personData = processPersonData(input, personInput);
        if (personData == null) return;

        boolean isVip = false;

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
            peopleVipQueue.offer(person);
        } else {
            peopleQueue.offer(person);
        }

        printPersonAddedMessage(input, person);
    }

    private void processEnterPerson(String input) {
        Person enteredPerson;
        if (peopleVipQueue.isEmpty()) {
            System.out.printf("VIP queue is already empty%n");
            System.out.printf("Please provide another command%n%n");
        } else {
            enteredPerson = peopleVipQueue.poll();
            printEnteredMessage(input, enteredPerson);
            return;
        }

        if (peopleQueue.isEmpty()) {
            System.out.printf("Queue is already empty%n");
            System.out.printf("Please provide another command%n%n");
        } else {
            enteredPerson = peopleQueue.poll();
            printEnteredMessage(input, enteredPerson);
        }
    }

    private void processLeavePerson(String input) {
        String personInput = extractPersonInput(input);
        String[] personData = processPersonData(input, personInput);
        if (personData == null) return;

        int personCounter;
        boolean isRemoved;

        if (input.contains("VIP")) {
            personCounter = "VIP".equals(personData[2]) ? 1 : Integer.parseInt(personData[2]);
            isRemoved = peopleVipQueue.remove(new Person(personData[0], personData[1], personCounter));
        } else {
            personCounter = personData.length < 3 ? 1 : Integer.parseInt(personData[2]);
            isRemoved = peopleQueue.remove(new Person(personData[0], personData[1], personCounter));
        }

        if (!isRemoved) {
            System.out.printf("Are you sure you meant %s%n", personInput);
            System.out.printf("%s is not standing in the line %n%n", personInput);
            return;
        }

        System.out.printf("%s%n", input);
        System.out.printf("Leaving queue: %s%n", personInput);
        System.out.printf("Queue: %s%n%n", getPeopleMergedList());
    }

    private String[] processPersonData(String input, String personInput) {
        String[] personData = personInput.split("[_,]");

        if (personData.length < 2) {
            printInvalidCommandMessage(input);
            return null;
        }
        return personData;
    }

    private String extractPersonInput(String input) {
        return input.substring(input.indexOf("(") + 1, input.indexOf(")"));
    }

    private Person createPerson(String personInput, String[] personData, boolean isVip) {
        int personCounter = peopleNameCounter.getOrDefault(personInput, 0) + 1;
        peopleNameCounter.put(personInput, personCounter);
        return isVip ? new Person(personData[0], personData[1], personCounter, true) : new Person(personData[0], personData[1], personCounter);
    }

    private List<Person> getPeopleMergedList() {
        List<Person> peopleMergedList = new ArrayList<>(peopleVipQueue);
        peopleMergedList.addAll(peopleQueue);
        return peopleMergedList;
    }

    private void printInvalidCommandMessage(String input) {
        System.out.printf("Provided command: %s%n", input);
        System.out.printf("Please provide correct command.%n%n");
    }

    private void printPersonAddedMessage(String input, Person person) {
        System.out.printf("%s%n", input);
        System.out.printf("%s came to the queue: %s%n", person, true);
        System.out.printf("Queue: %s%n%n", getPeopleMergedList());
    }

    private void printEnteredMessage(String input, Person enteredPerson) {
        System.out.printf("%s%n", input);
        System.out.printf("Processing queue: %s arrived at the store%n", enteredPerson);
        System.out.printf("Queue: %s%n%n", getPeopleMergedList());
    }
}
