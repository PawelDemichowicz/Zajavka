package workshop4.sql.services;

import workshop4.sql.domains.Command;

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

        Map<String, String> parameterMap = Arrays.asList(split).subList(1, split.length).stream()
                .map(item -> item.split("="))
                .collect(Collectors.toMap(
                        itemSplit -> itemSplit[0],
                        itemSplit -> itemSplit[1]
                ));
        return Optional.empty();
    }
}
