package com.ipv.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ipv.entity.Audit;
import com.ipv.service.AuditService;

/**
 * 
 * The REST API layer, injected the service, providing the connections for the frontend
 * 
 * The path start from /audits
 * For the system admin to track the logs
 * 
 */
@RestController
@RequestMapping("/audits")
public class AuditRestAPI {
	
	@Autowired
	private AuditService service;
	
	//reading all logs
	@GetMapping
	public List<Audit> findAll() {
		return service.findAll();
	}
	
	//querying logs by user id and the date range
	@GetMapping("/by_user/{id}")
	public List<Audit> getByUser(@PathVariable int id) {
		return service.findByUserId(id);
	}
	
	//querying logs by staff id and the date range
	@GetMapping("/by_staff/{id}")
	public List<Audit> getByStaff(@PathVariable int id) {
		return service.findByPostId(id);
	}
	
	
}










