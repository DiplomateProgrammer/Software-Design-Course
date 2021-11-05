package com.pushkin.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@Setter
public class Task {
    public enum TaskStatus {
        ACTIVE,
        FINISHED
    }
    private int id;
    private int list;
    private String description;
    private TaskStatus status;

    public Task() {
        this.description = "no_description";
        this.status = TaskStatus.ACTIVE;
    }


    public Task(int id, int list, String description) {
        this.id = id;
        this.list = list;
        this.description = description;
        this.status = TaskStatus.ACTIVE;
    }

    public Task copy() {
        return new Task(this.id, this.list, this.description);
    }

    public void markAsFinished() {
        if (status == TaskStatus.ACTIVE) {
            status = TaskStatus.FINISHED;
        }
    }

    private String statusToString() {
        switch (status) {
            case ACTIVE:
                return "active";
            case FINISHED:
                return "finished";
            default:
                return "unknown";
        }
    }

    @Override
    public String toString() {
        return String.format("Task '%s': %s", description, statusToString());
    }
}