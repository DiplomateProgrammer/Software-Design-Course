package com.pushkin.dao;

import com.pushkin.model.Task;
import com.pushkin.model.TaskList;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class TaskInMemoryDao implements TaskDao{
    private final AtomicInteger lastListId = new AtomicInteger(0);
    private final AtomicInteger lastTaskId = new AtomicInteger(0);
    private final Map<Integer, TaskList> taskLists = new ConcurrentHashMap<Integer, TaskList>();
    private final Map<Integer, Task> tasks = new ConcurrentHashMap<Integer, Task>();

    @Override
    public List<TaskList> getLists() {
        return taskLists.values().stream().map(TaskList::copy).collect(Collectors.toList());
    }

    @Override
    public List<Task> getTasksFromList(int listId) {
        List<Task> ret = tasks.values().stream().filter(t -> t.getList() == listId).collect(Collectors.toList());
        return List.copyOf(ret);
    }

    @Override
    public void addList(TaskList list) {
        list.setId(lastListId.incrementAndGet());
        taskLists.put(list.getId(), list);
    }

    @Override
    public void deleteList(int listId) {
        for (Task task: taskLists.get(listId).getTasks()) {
            deleteTask(task.getId());
        }
        taskLists.remove(listId);
    }

    @Override
    public void addTask(Task task) {
        int id = lastTaskId.incrementAndGet();
        task.setId(id);
        tasks.put(id, task);
    }

    @Override
    public void deleteTask(int taskId) {
        tasks.remove(taskId);
    }

    @Override
    public void markAsFinished(int taskId) {
        tasks.get(taskId).markAsFinished();
    }

}
