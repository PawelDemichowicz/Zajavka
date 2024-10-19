package workshop4.sql.domains;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Command {

    private final Type type;
    private final ToDoItem toDoItem;

    private final ToDoItem.Field sortBy;
    private final SortDirection sortDir;

    public Command(Type type, ToDoItem toDoItem, ToDoItem.Field sortBy, SortDirection sortDir) {
        this.type = type;
        this.toDoItem = toDoItem;
        this.sortBy = sortBy;
        this.sortDir = sortDir;
    }

    public Type getType() {
        return type;
    }

    public ToDoItem getToDoItem() {
        return toDoItem;
    }

    public ToDoItem.Field getSortBy() {
        return sortBy;
    }

    public SortDirection getSortDir() {
        return sortDir;
    }

    @Override
    public String toString() {
        return "Command{" +
                "type=" + type +
                ", toDoItem=" + toDoItem +
                '}';
    }

    public enum Type {
        CREATE("CREATE"),
        UPDATE("UPDATE"),
        READ("READ"),
        READ_ALL("READ ALL"),
        READ_GROUPED("READ GROUPED"),
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

        public static Type from(String input) {
            for (Type type : Type.values()) {
                if (type.name.equals(input)) {
                    return type;
                }
            }
            throw new IllegalArgumentException(input);
        }
    }

    public enum SortDirection {
        ASC,
        DESC
    }
}
