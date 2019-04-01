package com.ipv.service.imple;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipv.entity.Audit;
import com.ipv.reporsitory.AuditRepository;
import com.ipv.service.AuditService;

@Service
public class AuditServiceImple extends BaseImple<Audit> implements AuditService{
	
	@Autowired
	private AuditRepository auditRepository;
	
	@PostConstruct
	public void initParent() {
	  super.repository = auditRepository;
	}

}
