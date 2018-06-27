package com.erdelyiandras.job.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.erdelyiandras.job.entity.Rights;
import com.erdelyiandras.job.entity.User;
import com.erdelyiandras.job.repository.RightsRepository;
import com.erdelyiandras.job.repository.UserRepository;


@Controller    
@RequestMapping(path="/job/user")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RightsRepository rightsRepository;

	public UserController(UserRepository userRepository, RightsRepository rightsRepository) {
		this.userRepository = userRepository;
		this.rightsRepository = rightsRepository;
	}

	@PostMapping 
	public ResponseEntity<User> addNewUser(@RequestBody User user) {
		try {
			User savedUser = userRepository.save(user);
			return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<User>(user, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path="/all")
	public @ResponseBody Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}

	@DeleteMapping(path="/{id}") 
	public ResponseEntity<HttpStatus> removeUser(@PathVariable(value="id") Long id) {
		boolean taskExists = userRepository.existsById(id);

		if (taskExists) {
			userRepository.deleteById(id);	
			return ResponseEntity.ok().build();
		}
		return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
	}

	@PutMapping
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		boolean userExists = userRepository.existsById(user.getId());

		if (userExists) {
			userRepository.save(user);
			return ResponseEntity.ok().build();
		}else {
			return new ResponseEntity<User>(user, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path="/{id}")
	public ResponseEntity<User> getUserById(@PathVariable(value="id") Long id) {
		if (userRepository.existsById(id)) {
			Optional<User> result = userRepository.findById(id);
			User u = result.get();
			return new ResponseEntity<User>(u,HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping(path="/user_id/rigths_id")
	public ResponseEntity<HttpStatus> userToRights(@PathVariable(value="user_id") Long user_id, @PathVariable(value="rights_id") Long rights_id){
		Optional<User> userOptional = userRepository.findById(user_id);
		Optional<Rights> rightsOptional = rightsRepository.findById(rights_id);
		if (userOptional.isPresent() == true && rightsOptional.isPresent() == true) {
			User user = userOptional.get();
			Rights rights = rightsOptional.get();
			user.setRights(rights);
			userRepository.save(user);
			return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
	}

}