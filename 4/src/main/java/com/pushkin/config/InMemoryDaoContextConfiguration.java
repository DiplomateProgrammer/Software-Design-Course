package com.pushkin.config;

import com.pushkin.dao.TaskInMemoryDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class InMemoryDaoContextConfiguration {
    @Bean
    public TaskInMemoryDao taskInMemoryDao() {
        return new TaskInMemoryDao();
    }
}
