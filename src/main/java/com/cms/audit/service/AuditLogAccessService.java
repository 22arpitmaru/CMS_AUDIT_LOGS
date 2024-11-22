package com.cms.audit.service;

import com.cms.audit.dto.request.AuditLogAccessDTO;
import com.cms.audit.dto.response.ApiResponse;

public interface AuditLogAccessService {

    public ApiResponse saveAuditLog(AuditLogAccessDTO auditLogAccessDTO) ;

    public ApiResponse getAuditLogs(int page, int size, String sortBy, String searchTerm);

    public void excelGenerate(int page, int size, String sortBy) throws Exception;
}
