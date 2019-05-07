package com.ipv.service.imple;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ipv.entity.Audit;
import com.ipv.entity.Contact;
import com.ipv.reporsitory.AuditRepository;
import com.ipv.reporsitory.ContactRepository;

/**
 * 
 * Utilizing the Spring schedule work to periodically remove the expired contact information 
 * and the system logs for the privacy protection
 * 
 * */
@Component
public class ScheduledTasks {
	
	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private AuditRepository auditRepository;
	
	static final long WEEK = 1000 * 60 * 60 * 24 * 7;
	
	//The deletion will be excuted in every week
    @Scheduled(fixedRate = WEEK) // 1 week
    public void removingExpiredContacts() {
    	Date line = new Date(System.currentTimeMillis() - WEEK);
    	List<Contact> contacts = contactRepository.findAll();
    	for (Contact contact : contacts) {
    		if (contact.getDate().before(line)) {
    			contact.setAddress(null);
    			contact.setNumber(null);
    			contact.setDate(new Date());
    			contactRepository.save(contact);
    		}
    	}
    }
    
    //The deletion will be excuted in every 2 weeks
    @Scheduled(fixedRate = WEEK * 2) // 2 week
    public void removingExpiredLogs() {
    	Date line = new Date(System.currentTimeMillis() - WEEK * 2);
    	List<Audit> audits = auditRepository.findAll();
    	for (Audit audit : audits) {
    		if (audit.getDate().before(line)) {
    			auditRepository.delete(audit);
    		}
    	}
    }
}
