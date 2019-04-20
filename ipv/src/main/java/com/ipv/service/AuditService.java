package com.ipv.service;

import java.util.List;

import com.ipv.entity.Audit;

/**
 * 
 * Data persistence business logic layer
 * The interface-implementation architechture is required by the Spring framework (the multiple implementation is allowed)
 * There are many common interfaces between the services(like CRUD), so the common part is define in a BaseService
 * 
 * The service interface get the common methods by extends the BaseService
 * The other interfaces will be added later
 * 
 */
public interface AuditService extends BaseService<Audit>{
	public void addAudit(Integer userId, Integer postId, String action);
	public List<Audit> findByPostId(int id);
	public List<Audit> findByUserId(int id);
}
