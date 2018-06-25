package com.erdelyiandras.job.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.erdelyiandras.job.entity.Status;
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
	
	@GetMapping(path="/add") 
	public @ResponseBody String addNewTask (@RequestParam String title, @RequestParam String description,
			@RequestParam Status status) {

		Task t = new Task();
		t.setTitle(title);
		t.setDescription(description);
		t.setStatus(status);
		taskRepository.save(t);
		return "Saved";
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
	
	@PutMapping(path="/update/{id}")
	public @ResponseBody String updateTask (@PathVariable(value="id") Long id, @RequestParam String title,
			@RequestParam String description, @RequestParam Status status) {
		if (taskRepository.existsById(id)) {
			Optional<Task> result = taskRepository.findById(id);
			Task t = result.get();
			t.setTitle(title);
			t.setDescription(description);
			t.setStatus(status);
			taskRepository.save(t);
			return "Task updated";
		}else {
			return "Error";
		}
	}
}
