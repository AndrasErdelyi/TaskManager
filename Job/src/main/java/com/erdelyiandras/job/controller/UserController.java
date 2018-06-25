package com.erdelyiandras.job.controller;



import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.erdelyiandras.job.entity.User;
import com.erdelyiandras.job.repository.UserRepository;


@Controller    
@RequestMapping(path="/job/user")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping(path="/add") 
	public @ResponseBody String addNewUser (@RequestParam String name) {

		User u = new User();
		u.setName(name);
		u.setCreatedAt(new Date());
		u.setModifiedAt(new Date());
		userRepository.save(u);
		return "Saved";
	}
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	//@DeleteMapping(path="/remove/{id}") 
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public @ResponseBody String removeUser (@PathVariable(value="id") Long id) {
		try {
			userRepository.deleteById(id);
		} catch (Exception e) {
			return "Error";
		}
		return "Removed";
	}
	
	@PutMapping(path="/update/{id}")
	public @ResponseBody String updateUser (@PathVariable(value="id") Long id) {
		if (userRepository.existsById(id)) {
//			setName(name);
//			u.setModifiedAt(new Date());
//			userRepository.save(u);
			return "Updated";
		}else {
			return "Error";
		}
	} 
	
}