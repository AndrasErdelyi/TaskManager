package com.erdelyiandras.job.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.erdelyiandras.job.entity.Task;
import com.erdelyiandras.job.entity.User;
import com.erdelyiandras.job.repository.TaskRepository;
import com.erdelyiandras.job.repository.UserRepository;

@Controller
@RequestMapping(path="job/task")
public class TaskController {
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private UserRepository userRepository;

	public TaskController(TaskRepository taskRepository, UserRepository userRepository) {
		this.taskRepository = taskRepository;
		this.userRepository = userRepository;
	}

	@PostMapping
	public ResponseEntity<Task> addTask(@RequestBody Task task){
		try {
			Task savedTask = taskRepository.save(task);
			return new ResponseEntity<Task>(savedTask, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Task>(task, HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping(path="/{id}")
	public ResponseEntity<HttpStatus> removeTask (@PathVariable(value="id") Long id) {
		boolean taskExists = taskRepository.existsById(id);
		
		if (taskExists) {
			taskRepository.deleteById(id);	
			return ResponseEntity.ok().build();
		}
		return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
	}

	@PutMapping
	public ResponseEntity<Task> updateTask(@RequestBody Task task){
		boolean taskExists = taskRepository.existsById(task.getId());

		if (taskExists) {
			taskRepository.save(task);
			return ResponseEntity.ok().build();
		}else {
			return new ResponseEntity<Task>(task, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path="/{task_id}/{user_id}")
	public ResponseEntity<HttpStatus> taskToUser(@PathVariable(value="task_id") Long task_id, @PathVariable(value="user_id") Long user_id) {
		Optional<Task> taskOptional = taskRepository.findById(task_id);
		Optional<User> userOptional = userRepository.findById(user_id);
		if (taskOptional.isPresent() == true && userOptional.isPresent() == true) {
			Task task = taskOptional.get();
			User user = userOptional.get();
			task.setUser(user);
			taskRepository.save(task);
			return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
	}
}
