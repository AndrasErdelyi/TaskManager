package com.erdelyiandras.job.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.erdelyiandras.job.entity.Task;
import com.erdelyiandras.job.repository.TaskRepository;

@Controller
@RequestMapping(path="job/task")
public class TaskController {

	@Autowired
	private TaskRepository taskRepository;

	public TaskController(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	//	@GetMapping(path="/add") 
	//	public @ResponseBody String addNewTask (@RequestParam String title, @RequestParam String description,
	//			@RequestParam Status status) {
	//
	//		Task t = new Task();
	//		t.setTitle(title);
	//		t.setDescription(description);
	//		t.setStatus(status);
	//		taskRepository.save(t);
	//		return "Saved";
	//	}
	@PostMapping(path="/add")
	public ResponseEntity<Task> addTask(@RequestBody Task task){
		try {
			taskRepository.save(task);
			return new ResponseEntity<Task>(task, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Task>(task, HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping(path="/remove/{id}")
	public @ResponseBody String removeTask (@PathVariable(value="id") Long id) {
		try {
			taskRepository.deleteById(id);
		} catch (Exception e) {
			return "Error";
		}
		return "Task removed";
	}

	//	@PutMapping(path="/update/{id}")
	//	public @ResponseBody String updateTask (@PathVariable(value="id") Long id, @RequestParam String title,
	//			@RequestParam String description, @RequestParam Status status) {
	//		if (taskRepository.existsById(id)) {
	//			Optional<Task> result = taskRepository.findById(id);
	//			Task t = result.get();
	//			t.setTitle(title);
	//			t.setDescription(description);
	//			t.setStatus(status);
	//			taskRepository.save(t);
	//			return "Task updated";
	//		}else {
	//			return "Error";
	//		}
	//	}
	@PutMapping(path="/update/{id}")
	public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task){
		boolean taskExists = taskRepository.existsById(id);

		if (taskExists) {
			taskRepository.save(task);
			return ResponseEntity.ok().build();
		}else {
			return new ResponseEntity<Task>(task, HttpStatus.BAD_REQUEST);
		}

		//		try {
		//			if (taskRepository.existsById(id)) {
		//				taskRepository.save(task);
		//				return new ResponseEntity<Task>(task, HttpStatus.CREATED);
		//			}else {
		//				return new ResponseEntity<Task>(task, HttpStatus.BAD_REQUEST);
		//			}
		//		} catch (Exception e) {
		//			return new ResponseEntity<Task>(task, HttpStatus.BAD_REQUEST);
		//		}
	}
}
