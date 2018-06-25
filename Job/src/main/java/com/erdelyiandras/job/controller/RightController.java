package com.erdelyiandras.job.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.erdelyiandras.job.entity.Right;
import com.erdelyiandras.job.repository.RightRepository;

@Controller
@RequestMapping(path="/job/right")
public class RightController {

	@Autowired
	private RightRepository rightRepository;
	
	public RightController(RightRepository rightRepository) {
		this.rightRepository = rightRepository;
	}
	
	@GetMapping(path="/add") 
	public @ResponseBody String addNewUser (@RequestParam String name, @RequestParam int code) {

		Right r = new Right();
		r.setName(name);
		r.setCode(code);
		r.setCreatedAt(new Date());
		r.setModifiedAt(new Date());
		rightRepository.save(r);
		return "Saved";
	}
}
