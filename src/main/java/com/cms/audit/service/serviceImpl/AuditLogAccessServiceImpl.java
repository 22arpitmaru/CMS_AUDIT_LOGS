package com.cms.audit.service.serviceImpl;

import com.cms.audit.dto.request.AuditLogAccessDTO;
import com.cms.audit.dto.response.ApiResponse;
import com.cms.audit.entities.AuditLogAccess;
import com.cms.audit.mapper.AuditLogAccessMapper;
import com.cms.audit.repository.AuditLogAccessRepository;
import com.cms.audit.service.AuditLogAccessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditLogAccessServiceImpl implements AuditLogAccessService {

    private final AuditLogAccessRepository auditLogAccessRepository;
    private final AuditLogAccessMapper auditLogAccessMapper;


    public ApiResponse saveAuditLog(AuditLogAccessDTO auditLogAccessDTO) {
        log.info("Request received to save audit log: {}", auditLogAccessDTO);

        try {
            AuditLogAccess entity = auditLogAccessMapper.toEntity(auditLogAccessDTO);
            AuditLogAccess savedEntity = auditLogAccessRepository.save(entity);
            AuditLogAccessDTO dto = auditLogAccessMapper.toDTO(savedEntity);

            log.info("Audit log saved successfully: {}", dto);
            return ApiResponse.builder()
                    .statusCode(HttpStatusCode.valueOf(200).value())
                    .data(dto)
                    .message("Audit Data is saved successfully")
                    .build();
        } catch (Exception e) {
            log.error("Error occurred while saving audit log: {}", auditLogAccessDTO, e);
            return ApiResponse.builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Failed to save audit log.")
                    .build();
        }
    }


    public ApiResponse getAuditLogs(int page, int size, String sortBy, String searchTerm) {
        log.info("Fetching audit logs with page: {}, size: {}, sortBy: {}, searchTerm: {}", page, size, sortBy, searchTerm);

        Instant start = Instant.now();
        LocalDateTime startTime = LocalDateTime.ofInstant(start, ZoneId.systemDefault());

        Sort sort = Sort.by(Sort.Order.asc(sortBy));
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<AuditLogAccess> auditLogAccessPage;

        try {
            if (searchTerm != null && !searchTerm.isEmpty()) {
                auditLogAccessPage = auditLogAccessRepository.findBySearchTerm(searchTerm, pageRequest);
            } else {
                auditLogAccessPage = auditLogAccessRepository.findAll(pageRequest);
            }

            // Record the end time
            Instant end = Instant.now();
            LocalDateTime endTime = LocalDateTime.ofInstant(end, ZoneId.systemDefault());

            // Calculate the total time required
            Duration duration = Duration.between(start, end);
            long totalMinutes = duration.toMinutes();

            // Log the times
            log.info("Start Time: {}", startTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            log.info("End Time: {}", endTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            log.info("Total Time: {} minutes", totalMinutes);

            // Build and return response
            if (!auditLogAccessPage.isEmpty()) {
                log.info("Fetched {} audit logs successfully", auditLogAccessPage.getContent().size());
                return ApiResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .data(auditLogAccessPage)
                        .message(String.format("CMS audit data retrieved. Start time: %s, End time: %s, Total time: %d minutes",
                                startTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                                endTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                                totalMinutes))
                        .build();
            } else {
                log.warn("No audit logs found.");
                return ApiResponse.builder()
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .data(Collections.emptyList())
                        .message(String.format("No CMS audit data found. Start time: %s, End time: %s, Total time: %d minutes",
                                startTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                                endTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                                totalMinutes))
                        .build();
            }
        } catch (Exception e) {
            log.error("Error occurred while fetching audit logs with page: {}, size: {}, sortBy: {}, searchTerm: {}", page, size, sortBy, searchTerm, e);
            return ApiResponse.builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Failed to fetch audit logs.")
                    .build();
        }
    }


    public void excelGenerate(int page, int size, String sortBy) throws Exception {
        log.info("Request received to generate audit log Excel with page: {}, size: {}, sortBy: {}", page, size, sortBy);

        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String outputPath = System.getProperty("user.home") + "\\Documents\\CMS_AUDIT_LOG_" + timestamp + ".xlsx";

            Sort sort = Sort.by(Sort.Order.asc(sortBy));
            PageRequest pageRequest = PageRequest.of(page, size, sort);

            Page<AuditLogAccess> auditLogAccessPage = auditLogAccessRepository.findAll(pageRequest);
            generateAuditLogExcel(outputPath, auditLogAccessPage);

            log.info("Excel file generated successfully at: {}", outputPath);
        } catch (Exception e) {
            log.error("Error occurred while generating Excel file for audit logs", e);
            throw new Exception("Failed to generate Excel file for audit logs", e);
        }
    }

    // Helper method to generate Excel file
    public void generateAuditLogExcel(String outputPath, Page<AuditLogAccess> logEntries) throws IOException {
        log.info("Starting to generate Excel file at: {}", outputPath);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Audit Log Access");

        // Add header row
        Row headerRow = sheet.createRow(0);
        String[] headers = {
                "Event ID", "Timestamp", "Access Type", "Agent", "Agent ID",
                "Role", "IP Address", "Unique Case ID", "Actor", "Action",
                "From", "To", "File", "Session ID", "Logout Timestamp",
                "Session Duration", "Registration", "Item Updated", "Before", "After",
                "Case ID", "Claimant Name", "End Timestamp", "View Duration", "Device",
                "Browser", "Incident Type", "Incident Details", "System Component", "Error Code",
                "Error Message", "Resolved", "Resolution Details", "Action Type", "Event Offset"
        };

        // Create header cells
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(createHeaderCellStyle(workbook));
        }

        // Add data rows
        int rowNum = 1;
        for (AuditLogAccess logEntry : logEntries) {
            Row row = sheet.createRow(rowNum++);
            addRowToSheet(row, logEntry);
        }

        // Write to file
        try (FileOutputStream fileOut = new FileOutputStream(outputPath)) {
            workbook.write(fileOut);
            log.info("Excel file generated successfully at: {}", outputPath);
        }

        // Close the workbook
        workbook.close();
    }

    // Helper method to add data to a row
    private void addRowToSheet(Row row, AuditLogAccess logEntry) {
        int colNum = 0;
        row.createCell(colNum++).setCellValue(getSafeString(logEntry.getEventId()));
        row.createCell(colNum++).setCellValue(getSafeString(logEntry.getTimestamp()));
        row.createCell(colNum++).setCellValue(getSafeString(logEntry.getAccessType()));
        row.createCell(colNum++).setCellValue(getSafeString(logEntry.getAgent()));
        row.createCell(colNum++).setCellValue(getSafeString(logEntry.getAgentId()));
        row.createCell(colNum++).setCellValue(getSafeString(logEntry.getRole()));
        row.createCell(colNum++).setCellValue(getSafeString(logEntry.getIpAddress()));
        row.createCell(colNum++).setCellValue(getSafeString(logEntry.getUniqueCaseId()));
        row.createCell(colNum++).setCellValue(getSafeString(logEntry.getActor()));
        row.createCell(colNum++).setCellValue(getSafeString(logEntry.getAction()));
        row.createCell(colNum++).setCellValue(getSafeString(logEntry.getFrom()));
        row.createCell(colNum++).setCellValue(getSafeString(logEntry.getTo()));
        row.createCell(colNum++).setCellValue(getSafeString(logEntry.getFile()));
        row.createCell(colNum++).setCellValue(getSafeString(logEntry.getSessionId()));
        row.createCell(colNum++).setCellValue(getSafeString(logEntry.getLogoutTimestamp()));
        row.createCell(colNum++).setCellValue(getSafeString(logEntry.getSessionDuration()));
        row.createCell(colNum++).setCellValue(getSafeString(logEntry.getRegistration()));
        row.createCell(colNum++).setCellValue(getSafeString(logEntry.getItemUpdated()));
        row.createCell(colNum++).setCellValue(getSafeString(logEntry.getBefore()));
        row.createCell(colNum++).setCellValue(getSafeString(logEntry.getAfter()));
        row.createCell(colNum++).setCellValue(getSafeString(logEntry.getCaseId()));
        row.createCell(colNum++).setCellValue(getSafeString(logEntry.getClaimantName()));
        row.createCell(colNum++).setCellValue(getSafeString(logEntry.getEndTimestamp()));
        row.createCell(colNum++).setCellValue(getSafeString(logEntry.getViewDuration()));
        row.createCell(colNum++).setCellValue(getSafeString(logEntry.getDevice()));
        row.createCell(colNum++).setCellValue(getSafeString(logEntry.getBrowser()));
        row.createCell(colNum++).setCellValue(getSafeString(logEntry.getIncidentType()));
        row.createCell(colNum++).setCellValue(getSafeString(logEntry.getIncidentDetails()));
        row.createCell(colNum++).setCellValue(getSafeString(logEntry.getSystemComponent()));
        row.createCell(colNum++).setCellValue(getSafeString(logEntry.getErrorCode()));
        row.createCell(colNum++).setCellValue(getSafeString(logEntry.getErrorMessage()));
        row.createCell(colNum++).setCellValue(getSafeString(logEntry.getResolved()));
        row.createCell(colNum++).setCellValue(getSafeString(logEntry.getResolutionDetails()));
        row.createCell(colNum++).setCellValue(getSafeString(logEntry.getActionType()));
        row.createCell(colNum++).setCellValue(getSafeString(logEntry.getEventOffset()));
    }

    // Helper method for safe string conversion
    private static String getSafeString(Object value) {
        if (value == null) {
            return "N/A";
        } else if (value instanceof LocalDateTime) {
            return ((LocalDateTime) value).format(DateTimeFormatter.ISO_DATE_TIME);
        } else if (value instanceof Duration) {
            return value.toString();
        } else {
            return value.toString();
        }
    }

    // Helper method to create header cell style
    private CellStyle createHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }
}
