package com.pushkin.dao;

import com.pushkin.model.Task;
import com.pushkin.model.TaskList;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.sql.DataSource;
import java.util.List;

public class TaskJdbcDao extends JdbcDaoSupport implements TaskDao {

    public TaskJdbcDao(DataSource dataSource) {
        super();
        setDataSource(dataSource);
    }

    @Override
    public List<TaskList> getLists() {
        String sql = "SELECT * FROM lists ORDER BY id";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(TaskList.class));
    }

    @Override
    public List<Task> getTasksFromList(int listId) {
        String sql = String.format("SELECT * FROM tasks WHERE list = %d ORDER BY id", listId);
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(Task.class));
    }

    @Override
    public void addList(TaskList taskList) {
        String sql = "INSERT INTO lists (name) VALUES (?)";
        getJdbcTemplate().update(sql, taskList.getName());
    }

    @Override
    public void deleteList(int listId) {
        getJdbcTemplate().update("DELETE FROM tasks WHERE list = ?", listId);
        getJdbcTemplate().update("DELETE FROM lists WHERE id = ?", listId);
    }

    @Override
    public void addTask(Task task) {
        String sql = "INSERT INTO tasks (list, description, status) VALUES (?, ?, ?)";
        getJdbcTemplate().update(sql, task.getList(), task.getDescription(), Task.TaskStatus.ACTIVE.ordinal());
    }

    @Override
    public void deleteTask(int taskId) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        getJdbcTemplate().update(sql, taskId);
    }

    private void changeTaskStatus(int taskId, Task.TaskStatus status) {
        String sql = "UPDATE tasks SET status = ? WHERE id = ?";
        getJdbcTemplate().update(sql, status.ordinal(), taskId);
    }

    @Override
    public void markAsFinished(int taskId) {
        changeTaskStatus(taskId, Task.TaskStatus.FINISHED);
    }

}
