package workshop4.sql;

import workshop4.sql.domains.Command;
import workshop4.sql.services.CommandService;
import workshop4.sql.services.DatabaseRunner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        List<String> list = List.of(
                "DELETE ALL;",
                "CREATE;NAME=TASK1;DESCRIPTION=SOME DESCRIPTION1;DEADLINE=11.02.2021 20:10;PRIORITY=0",
                "CREATE;NAME=TASK2;DESCRIPTION=SOME DESCRIPTION2;DEADLINE=12.02.2021 20:10;PRIORITY=1",
                "CREATE;NAME=TASK3;DESCRIPTION=SOME DESCRIPTION3;DEADLINE=13.02.2021 20:10;PRIORITY=2",
                "CREATE;NAME=TASK4;DESCRIPTION=SOME DESCRIPTION4;DEADLINE=14.02.2021 20:10;PRIORITY=3",
                "CREATE;NAME=TASK5;DESCRIPTION=SOME DESCRIPTION5;DEADLINE=15.02.2021 20:10;PRIORITY=4",
                "UPDATE;NAME=TASK3;DESCRIPTION=SOME NEW DESCRIPTION;DEADLINE=14.02.2021 20:10;PRIORITY=10",
                "READ;NAME=TASK1",
                "READ ALL;SORT=PRIORITY,ASC;",
                "DELETE;NAME=TASK4",
                "READ ALL;",
                "DELETE ALL;"
        );
        CommandService commandService = new CommandService();
        DatabaseRunner databaseRunner = new DatabaseRunner();

        List<Command> commands = list.stream()
                .map(commandService::buildCommand)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        commands.forEach(databaseRunner::run);
    }
}