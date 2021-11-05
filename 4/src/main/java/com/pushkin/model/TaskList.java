package com.pushkin.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Getter
@Setter
public class TaskList {
    private int id;
    private String name;
    private List<Task> tasks;

    public TaskList() {
        tasks = new CopyOnWriteArrayList<>();
    }

    public TaskList(int id, String name) {
        this.id = id;
        this.name = name;
        this.tasks = new CopyOnWriteArrayList<>();
    }

    public TaskList copy() {
        TaskList ret = new TaskList(this.id, this.name);
        ret.tasks = tasks.stream().map(Task::copy).collect(Collectors.toList());
        return ret;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    @Override
    public String toString() {
        return "List '" + name + "':";
    }
}

