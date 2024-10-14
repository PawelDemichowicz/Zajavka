package workshop4.sql.domains;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ToDoItem {
    private String name;
    private String description;
    private LocalDateTime deadLine;
    private Integer priority;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDeadLine() {
        return deadLine;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeadLine(LocalDateTime deadLine) {
        this.deadLine = deadLine;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
