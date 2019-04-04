package com.ipv.rest;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ipv.entity.Audit;
import com.ipv.exception.NotFoundException;
import com.ipv.service.AuditService;


@RestController
@RequestMapping("/audits")
public class AuditRestAPI {
	
	@Autowired
	private AuditService service;
	
	@GetMapping
	public List<Audit> findAll() {
		return service.findAll();
	}

	@GetMapping("/get_user/{id}")
	public List<Audit> get(@PathVariable int id) {
		return Arrays.asList();
	}
	
	@PostMapping
	public Audit add(@RequestBody Audit audit) {
		// just in case they pass an id in JSON ... set id to 0 this is to force a save of new item ... instead of update
		audit.setId(0);
		service.save(audit);
		return audit;
	}
	
	
	@PutMapping
	public Audit update(@RequestBody Audit audit) {
		service.save(audit);
		return audit;
	}
	
	@DeleteMapping("{id}")
	public String delete(@PathVariable int id) {
		
		Audit audit = service.findById(id);
		if (audit == null) {
			throw new NotFoundException("audit id not found - " + id);
		}
		service.deleteById(id);
		return "Deleted Audit id - " + id;
	}
	
}










