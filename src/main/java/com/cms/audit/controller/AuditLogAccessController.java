package com.cms.audit.controller;

import com.cms.audit.dto.request.AuditLogAccessDTO;
import com.cms.audit.dto.response.ApiResponse;
import com.cms.audit.service.AuditLogAccessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cms")
@RequiredArgsConstructor
@Slf4j
public class AuditLogAccessController {

    private final AuditLogAccessService auditLogAccessService;

    @PostMapping("/logs/save")
    public ApiResponse createAuditLog(@RequestBody AuditLogAccessDTO auditLogAccess) {
        log.info("Request received to save audit log: {}", auditLogAccess);
        return auditLogAccessService.saveAuditLog(auditLogAccess);
    }


    @GetMapping("/getAll")
    public ApiResponse getAllAuditLogs(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "eventId") String sortBy,
            @RequestParam(value = "searchTerm", required = false) String searchTerm
    ) {
        log.info("Request received to fetch audit logs with page: {}, size: {}, sortBy: {}, searchTerm: {}",
                page, size, sortBy, searchTerm);

        return auditLogAccessService.getAuditLogs(page, size, sortBy, searchTerm);

    }

    // Endpoint for generating audit log PDF reports
    @GetMapping("/generate-excel-file")
    public String generateAuditLogs(@RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(value = "size", defaultValue = "10") int size,
                                    @RequestParam(value = "sortBy", defaultValue = "eventId") String sortBy) throws Exception {
        log.info("Request received to generate audit log PDF with page: {}, size: {}, sortBy: {}", page, size, sortBy);

        try {
            auditLogAccessService.excelGenerate(page, size, sortBy);
            log.info("Audit log PDF generated successfully for page: {}, size: {}, sortBy: {}", page, size, sortBy);
            return "CMS Audit log PDF successfully Generated!";
        } catch (Exception e) {
            log.error("Error occurred while generating audit log PDF for page: {}, size: {}, sortBy: {}", page, size, sortBy, e);
            throw new Exception("Failed to generate PDF for audit logs", e);
        }
    }
}
