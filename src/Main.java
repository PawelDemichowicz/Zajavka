import java.util.List;

public class Main {
    public static void main(String[] args) {
        PeopleQueue people = new PeopleQueue();
        List<String> input = List.of(
                "ADD PERSON(Tomasz_Romański)",
                "ADD PERSON(Rafał_Adamczuk)",
                "ADD PERSON(Tomasz_Romański)",
                "ADD PERSON(Mariusz_Wilczek_VIIIP)",
                "ADD PERSON(Mariusz_Wilczkowski_VIP)",
                "ADD PERSON(Mariusz_Wilczkowski_VIP)",
                "ADD PERSON(Zbigniew_Ratownik)",
                "ADD PERSON(Zbigniew_Ratownik,VIP)",
                "PROCESS",
                "LEAVE PERSON(Mariusz_Wilczkowski)",
                "LEAVE PERSON(Tomasz_Romański_2)",
                "LEAVE PERSON(Mariusz_Wilczekkk)",
                "PROCESS",
                "ADD PERSON(Zbigniew_)",
                "LEAVE PERSON(Mariusz_)",
                "PROCESS",
                "LEAVE PERSON(Tomasz_Romański_2)"
        );

        for (String command : input) {
            people.processInput(command);
        }
    }
}
