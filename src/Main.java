import java.util.List;

public class Main {
    public static void main(String[] args) {
        PeopleQueue people = new PeopleQueue();
        List<String> input = List.of(
                "ADD PERSON(Tomasz_Romański)",
                "ADD PERSON(Rafał_Adamczuk)",
                "ADD PERSON(Tomasz_Romański)",
                "ADD PERSON(Mariusz_Wilczek)",
                "ADD PERSON(Zbigniew_Ratownik)",
                "PROCESS",
                "LEAVE PERSON(Tomasz_Romański_2)",
                "LEAVE PERSON(Mariusz_Wilczek)",
                "PROCESS",
                "PROCESS",
                "PROCESS"
        );

        for (String command : input) {
            people.processInput(command);
        }
    }
}
