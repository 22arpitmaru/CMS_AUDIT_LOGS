package com.cms.audit.entities;

import com.cms.audit.enums.ActionTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditLogAccess {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long eventId;
        private LocalDateTime timestamp;
        private String accessType;
        private String agent;
        private String agentId;
        private String role;
        private String ipAddress;
        private String uniqueCaseId;
        private String actor;
        private String action;
        @Column(name = "from_column")
        private String from;
        @Column(name = "to_column")
        private String to;
        private String file;
        private String sessionId;
        private LocalDateTime logoutTimestamp;
        private Duration sessionDuration;
        private String registration;
        private String itemUpdated;
        @Column(name = "before_change")
        private String before;
        @Column(name = "after_change")
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
        @Enumerated(EnumType.STRING)
        private ActionTypeEnum actionType;
        private String eventOffset;
}



