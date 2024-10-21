package workshop4.sql.services;

import workshop4.sql.domains.Command;
import workshop4.sql.domains.ToDoItem;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class CommandService {

    public Optional<Command> buildCommand(final String line) {
        String[] split = line.split(";");
        String commandTypeSplit = split[0];
        if (!Command.Type.getValues().contains(commandTypeSplit)) {
            System.err.printf("Unknown provided command: [%s]%n", commandTypeSplit);
            return Optional.empty();
        }

        Map<String, String> parametersMap = Arrays.asList(split).subList(1, split.length).stream()
                .map(item -> item.split("="))
                .collect(Collectors.toMap(
                        itemSplit -> itemSplit[0],
                        itemSplit -> itemSplit[1]
                ));

        List<String> sortingParams = Optional.ofNullable(parametersMap.get(ToDoItem.Field.SORT.name()))
                .map(params -> List.of(params.split(",")))
                .orElse(Collections.emptyList());

        Command.Type commandType = Command.Type.from(commandTypeSplit);
        ToDoItem toDoItem = buildToDoItem(commandType, parametersMap);
        return Optional.of(new Command(
                commandType,
                toDoItem,
                findSortingField(sortingParams),
                findSortingDir(sortingParams))
        );
    }

    private ToDoItem.Field findSortingField(List<String> sortingParams) {
        if (sortingParams.isEmpty()) {
            return ToDoItem.Field.NAME;
        }

        try {
            return ToDoItem.Field.valueOf(sortingParams.get(0));
        } catch (Exception e) {
            System.err.printf("Sorting field is not specified. Default: [%s]%n", ToDoItem.Field.NAME);
            return ToDoItem.Field.NAME;
        }
    }

    private Command.SortDirection findSortingDir(List<String> sortingParams) {
        if (sortingParams.isEmpty()) {
            return Command.SortDirection.ASC;
        }

        try {
            return Command.SortDirection.valueOf(sortingParams.get(1));
        } catch (Exception e) {
            System.err.printf("Sorting direction is not specified. Default: [%s]%n", Command.SortDirection.ASC);
            return Command.SortDirection.ASC;
        }
    }

    private ToDoItem buildToDoItem(final Command.Type commandType, Map<String, String> parametersMap) {
        ToDoItem toDoItem = new ToDoItem();
        Optional.ofNullable(parametersMap.get(ToDoItem.Field.NAME.name()))
                .ifPresent(toDoItem::setName);

        Optional.ofNullable(parametersMap.get(ToDoItem.Field.DESCRIPTION.name()))
                .ifPresent(toDoItem::setDescription);

        Optional.ofNullable(parametersMap.get(ToDoItem.Field.DEADLINE.name()))
                .ifPresent(deadLine -> toDoItem.setDeadLine(LocalDateTime.parse(deadLine, ToDoItem.DATE_FORMAT)));

        Optional.ofNullable(parametersMap.get(ToDoItem.Field.PRIORITY.name()))
                .ifPresent(priority -> toDoItem.setPriority(Integer.valueOf(priority)));

        Optional.of(commandType)
                .filter(Command.Type.COMPLETED::equals)
                .ifPresent(completed -> toDoItem.setStatus(ToDoItem.Status.COMPLETED));
        return toDoItem;
    }
}
