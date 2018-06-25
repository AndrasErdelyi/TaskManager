package com.erdelyiandras.job.repository;

import org.springframework.data.repository.CrudRepository;

import com.erdelyiandras.job.entity.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {

}
