package com.crud.tasks.repository;

import com.crud.tasks.domain.Task;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface TaskRepository extends CrudRepository <Task, Long>, JpaSpecificationExecutor<Task> {
    @Override
    List<Task> findAll();

    Task findById(Long id);
}
