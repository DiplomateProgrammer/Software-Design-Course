package com.pushkin.controller;

import com.pushkin.dao.TaskDao;
import com.pushkin.model.Task;
import com.pushkin.model.TaskList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TaskController {

    private final TaskDao taskDao;

    public TaskController(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @RequestMapping(value = "/lists", method = RequestMethod.GET)
    public String showTaskLists(ModelMap map) {
        List<TaskList> lists = taskDao.getLists();
        lists.forEach(l -> taskDao.getTasksFromList(l.getId()).forEach(l::addTask));
        prepareModelMap(map, lists);
        return "index";
    }

    @RequestMapping(value = "/add-list", method = RequestMethod.POST)
    public String addList(@ModelAttribute("taskList") TaskList taskList) {
        taskDao.addList(taskList);
        return "redirect:/lists";
    }

    @RequestMapping(value = "/add-task", method = RequestMethod.POST)
    public String addTask(@ModelAttribute("task") Task task) {
        taskDao.addTask(task);
        return "redirect:/lists";
    }

    @RequestMapping(value = "/delete-list", method = RequestMethod.POST)
    public String deleteList(@RequestParam(name = "taskListId") int listId) {
        taskDao.deleteList(listId);
        return "redirect:/lists";
    }

    @RequestMapping(value = "/mark-task", method = RequestMethod.POST)
    public String markAsFinished(@RequestParam(name = "taskId") int taskId) {
        taskDao.markAsFinished(taskId);
        return "redirect:/lists";
    }

    @RequestMapping(value = "/delete-task", method = RequestMethod.POST)
    public String deleteTask(@RequestParam(name = "taskId") int taskId) {
        taskDao.deleteTask(taskId);
        return "redirect:/lists";
    }

    private void prepareModelMap(ModelMap map, List<TaskList> lists) {
        map.addAttribute("taskLists", lists);
        map.addAttribute("taskList", new TaskList());
        map.addAttribute("task", new Task());
    }
}