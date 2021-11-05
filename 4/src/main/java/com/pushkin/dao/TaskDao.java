package com.pushkin.dao;

import com.pushkin.model.Task;
import com.pushkin.model.TaskList;

import java.util.List;

public interface TaskDao {
    List<TaskList> getLists();

    List<Task> getTasksFromList(int listId);

    void addList(TaskList list);

    void deleteList(int listId);

    void addTask(Task task);

    void deleteTask(int taskId);

    void markAsFinished(int taskId);
}
