package com.cms.audit.repository;

import com.cms.audit.dto.request.AuditLogAccessDTO;
import com.cms.audit.entities.AuditLogAccess;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogAccessRepository extends JpaRepository<AuditLogAccess ,Long> {
    @Query("SELECT a FROM AuditLogAccess a WHERE a.agent LIKE %:searchTerm%")
    Page<AuditLogAccess> findBySearchTerm(String searchTerm, Pageable pageable);

}
