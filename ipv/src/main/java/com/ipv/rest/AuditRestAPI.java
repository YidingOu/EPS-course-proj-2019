package com.ipv.rest;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ipv.entity.Audit;
import com.ipv.service.AuditService;
import com.ipv.util.wrapper.QueryByDateWapper;

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
	@PostMapping("/by_user")
	public List<Audit> getByUser(@RequestBody QueryByDateWapper requeryByDateBody) {
		return Arrays.asList();
	}
	
	//querying logs by staff id and the date range
	@PostMapping("/by_staff")
	public List<Audit> getByStaff(@RequestBody QueryByDateWapper requeryByDateBody) {
		return Arrays.asList();
	}
	
	
}










