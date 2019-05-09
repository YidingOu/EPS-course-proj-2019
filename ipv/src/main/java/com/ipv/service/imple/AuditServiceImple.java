package com.ipv.service.imple;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipv.entity.Audit;
import com.ipv.reporsitory.AuditRepository;
import com.ipv.service.AuditService;

/**
 * Data persistence business logic layer
 * The interface-implementation architechture is required by the Spring framework (the multiple implementation is allowed)
 * The service implementations implement the actual methods and will be injected into required components(like other services or restAPI layer)
 * <p>
 * There are many common method between the services(like CRUD), so the common part is define in a BaseImple
 * This service implements get the common methods by extends the BaseService
 * The E is the Entity type of the service
 */
@Service
public class AuditServiceImple extends BaseImple<Audit> implements AuditService {

    //Spring Dependency injection
    @Autowired
    private AuditRepository auditRepository;

    //After the injection is done, override the repository in the super class
    @PostConstruct
    public void initParent() {
        super.repository = auditRepository;
    }

    // Add an audit
    @Override
    public void addAudit(Integer userId, Integer postId, String action) {
        Audit audit = new Audit();
        audit.setAction(action);
        if (userId != null) {
            audit.setUserId(userId);
        }
        if (postId != null) {
            audit.setUserId(postId);
        }
        auditRepository.save(audit);
    }

    // Find a list of audits with input post id
    @Override
    public List<Audit> findByPostId(int id) {
        return auditRepository.findByPostId(id);
    }

    // Find a list of audits with input user id
    @Override
    public List<Audit> findByUserId(int id) {
        return auditRepository.findByUserId(id);
    }

}
