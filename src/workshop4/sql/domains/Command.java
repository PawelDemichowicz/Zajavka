package workshop4.sql.domains;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Command {

    private final Type type;
    private final ToDoItem toDoItem;

    public Command(Type type, ToDoItem toDoItem) {
        this.type = type;
        this.toDoItem = toDoItem;
    }

    public enum Type {
        CREATE("CREATE"),
        UPDATE("UPDATE"),
        READ("READ"),
        READ_ALL("READ ALL"),
        DELETE("DELETE"),
        DELETE_ALL("DELETE ALL");

        private final String name;

        Type(String name) {
            this.name = name;
        }

        public static List<String> getValues() {
            return Stream.of(values())
                    .map(Type::getName)
                    .collect(Collectors.toList());
        }

        public String getName() {
            return name;
        }
    }
}
