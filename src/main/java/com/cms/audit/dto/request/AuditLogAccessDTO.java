package com.cms.audit.dto.request;

import com.cms.audit.enums.ActionTypeEnum;
import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
public class AuditLogAccessDTO {
    private LocalDateTime timestamp;
    private String accessType;
    private String agent;
    private String agentId;
    private String role;
    private String ipAddress;
    private String uniqueCaseId;
    private String actor;
    private String action;
    private String from;
    private String to;
    private String file;
    private String sessionId;
    private LocalDateTime logoutTimestamp;
    private Duration sessionDuration;
    private String registration;
    private String itemUpdated;
    private String before;
    private String after;
    private String caseId;
    private String claimantName;
    private LocalDateTime endTimestamp;
    private Duration viewDuration;
    private String device;
    private String browser;
    private String incidentType;
    private String incidentDetails;
    private String systemComponent;
    private String errorCode;
    private String errorMessage;
    private Boolean resolved;
    private String resolutionDetails;
    private ActionTypeEnum actionType;
    private String eventOffset;
}
