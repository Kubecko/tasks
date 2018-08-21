package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DbService {
    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTask(){
        return taskRepository.findAll();
    }

    public Task getByTaskId(Long taskId){ return taskRepository.findById(taskId); }

    public Task deleteTaskId(Long deleteTaskId){
        return taskRepository.deleteById(deleteTaskId);
    }
}
