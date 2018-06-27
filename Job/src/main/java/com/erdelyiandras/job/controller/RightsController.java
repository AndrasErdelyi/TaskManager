package com.erdelyiandras.job.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.erdelyiandras.job.entity.Rights;
import com.erdelyiandras.job.repository.RightsRepository;

@Controller
@RequestMapping(path="/job/rights")
public class RightsController {

	@Autowired
	private RightsRepository rightsRepository;
	
	public RightsController(RightsRepository rightsRepository) {
		this.rightsRepository = rightsRepository;
	}
	
	@PostMapping 
	public ResponseEntity<Rights> addNewRights(@RequestBody Rights rights) {
		try {
			Rights savedRights = rightsRepository.save(rights);
			return new ResponseEntity<Rights>(savedRights, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Rights>(rights, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping
	public @ResponseBody Iterable<Rights> getAllUsers() {
		return rightsRepository.findAll();
	}
	
	@PutMapping
	public ResponseEntity<Rights> updateRights(@RequestBody Rights rights){
		boolean taskExists = rightsRepository.existsById(rights.getId());

		if (taskExists) {
			rightsRepository.save(rights);
			return ResponseEntity.ok().build();
		}else {
			return new ResponseEntity<Rights>(rights, HttpStatus.BAD_REQUEST);
		}
	}
}
