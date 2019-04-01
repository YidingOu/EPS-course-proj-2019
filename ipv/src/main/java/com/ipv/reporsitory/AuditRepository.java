package com.ipv.reporsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ipv.entity.Audit;


public interface AuditRepository extends JpaRepository<Audit, Integer>{

}
