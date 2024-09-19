import java.util.List;

public class Main {
    public static void main(String[] args) {
        PeopleQueue people = new PeopleQueue();
        List<String> input = List.of(
                "ADD PERSON(Tomasz_Romański)",
                "ADD PERSON(RafałAdamczuk)",
                "ADD PERSON(Tomasz_Romański)",
                "ADD PERSON(Mariusz_Wilczek_VIIIP)",
                "ADD PERSON(Mariusz_Wilczkowski_VIP)",
                "ADD PERSON(Mariusz_Wilczkowski)",
                "ADD PERSON(Mariusz_Wilczkowski_VIP)",
                "ADD PERSON(Mariusz_Wilczkowski_VIP)",
                "ADD PERSON(Mariusz_Wilczkowski_VIP)",
                "LEAVE PERSON(Mariusz_Wilczkowski_VIP)",
                "LEAVE PERSON(Mariusz_Wilczkowski_VIP)",
                "LEAVE PERSON(Mariusz_Wilczkowski_VIP)",
                "LEAVE PERSON(Mariusz_Wilczkowski_VIP)",
                "ADD PERSON(Zbigniew_)",
                "LEAVE PERSON(Mariusz_)",
                "PROCESS"
        );

        for (String command : input) {
            people.processInput(command);
        }
    }
}
