import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

public class PeopleQueue {
    private final HashMap<String, Integer> peopleNameCounter = new HashMap<>();
    private final Queue<Person> peopleQueue = new ArrayDeque<>();

    void processInput(String input) {
        if (input.contains("ADD PERSON")) {
            processAddPerson(input);
        } else if ("PROCESS".equals(input)) {
            processMovePerson(input);
        } else if (input.contains("LEAVE PERSON")) {
            processLeavePerson(input);
        }
    }

    private void processAddPerson(String input) {
        String personInput = input.substring(input.indexOf("(") + 1, input.indexOf(")"));
        String[] personData = personInput.split("_");
        String personName = personData[0];
        String personSurname = personData[1];

        int personCounter = peopleNameCounter.getOrDefault(personInput, 0) + 1;
        peopleNameCounter.put(personInput, personCounter);

        Person person = new Person(personName, personSurname, personCounter);

        boolean personAdded = peopleQueue.offer(person);

        System.out.printf("%s%n", input);
        System.out.printf("%s came to the queue: %s%n", person, personAdded);
        System.out.printf("Queue: %s%n%n", peopleQueue);
    }

    private void processLeavePerson(String input) {

    }

    private void processMovePerson(String input) {

    }
}
