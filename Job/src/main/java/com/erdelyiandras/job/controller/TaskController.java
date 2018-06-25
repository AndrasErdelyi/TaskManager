package com.erdelyiandras.job.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
		t.setCreatedAt(new Date());
		t.setModifiedAt(new Date());
		taskRepository.save(t);
		return "Saved";
	}
}
