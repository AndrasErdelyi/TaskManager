package com.erdelyiandras.job.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@GetMapping(path="/add") 
	public @ResponseBody String addNewRights (@RequestParam String name, @RequestParam int code) {

		Rights r = new Rights();
		r.setName(name);
		r.setCode(code);
		r.setCreatedAt(new Date());
		r.setModifiedAt(new Date());
		rightsRepository.save(r);
		return "Saved";
	}
}
