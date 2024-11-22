package com.cms.audit.mapper;

import com.cms.audit.dto.request.AuditLogAccessDTO;
import com.cms.audit.entities.AuditLogAccess;
import org.springframework.stereotype.Service;

@Service
public class AuditLogAccessMapping {

    private final AuditLogAccessMapper mapper = AuditLogAccessMapper.INSTANCE;

    public AuditLogAccessDTO convertEntityToDTO(AuditLogAccess auditLogAccess) {
        return mapper.toDTO(auditLogAccess);
    }

    public AuditLogAccess convertDTOToEntity(AuditLogAccessDTO auditLogAccessDTO) {
        return mapper.toEntity(auditLogAccessDTO);
    }
}
