package workshop4.sql.services;

import workshop4.sql.domains.Command;
import workshop4.sql.domains.ToDoItem;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class CommandService {

    public Optional<Command> buildCommand(final String line) {
        String[] split = line.split(";");
        String commandType = split[0];
        if (!Command.Type.getValues().contains(commandType)) {
            System.err.printf("Unknown provided command: [%s]%n", commandType);
            return Optional.empty();
        }

        Map<String, String> parametersMap = Arrays.asList(split).subList(1, split.length).stream()
                .map(item -> item.split("="))
                .collect(Collectors.toMap(
                        itemSplit -> itemSplit[0],
                        itemSplit -> itemSplit[1]
                ));

        ToDoItem toDoItem = buildToDoItem(parametersMap);
        return Optional.of(new Command(Command.Type.from(commandType), toDoItem));
    }

    private ToDoItem buildToDoItem(Map<String, String> parametersMap) {
        ToDoItem toDoItem = new ToDoItem();
        Optional.ofNullable(parametersMap.get(ToDoItem.Field.NAME.name()))
                .ifPresent(toDoItem::setName);
        Optional.ofNullable(parametersMap.get(ToDoItem.Field.DESCRIPTION.name()))
                .ifPresent(toDoItem::setDescription);
        Optional.ofNullable(parametersMap.get(ToDoItem.Field.DEADLINE.name()))
                .ifPresent(deadLine -> toDoItem.setDeadLine(LocalDateTime.parse(deadLine, ToDoItem.DATE_FORMAT)));
        Optional.ofNullable(parametersMap.get(ToDoItem.Field.PRIORITY.name()))
                .ifPresent(priority -> toDoItem.setPriority(Integer.valueOf(priority)));
        return toDoItem;
    }
}
