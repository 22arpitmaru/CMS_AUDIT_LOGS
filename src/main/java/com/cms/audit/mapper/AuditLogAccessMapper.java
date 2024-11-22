package com.cms.audit.mapper;

import com.cms.audit.dto.request.AuditLogAccessDTO;
import com.cms.audit.entities.AuditLogAccess;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface AuditLogAccessMapper {
    AuditLogAccessMapper INSTANCE = Mappers.getMapper(AuditLogAccessMapper.class);

//    @Mapping(source = "accessType", target = "accessType")
//    @Mapping(source = "agent", target = "agent")
//    @Mapping(source = "agentId", target = "agentId")
//    @Mapping(source = "role", target = "role")
//    @Mapping(source = "ipAddress", target = "ipAddress")
//    @Mapping(source = "uniqueCaseId", target = "uniqueCaseId")
//    @Mapping(source = "actor", target = "actor")
//    @Mapping(source = "action", target = "action")
//    @Mapping(source = "from", target = "from")
//    @Mapping(source = "to", target = "to")
//    @Mapping(source = "file", target = "file")
//    @Mapping(source = "sessionId", target = "sessionId")
//    @Mapping(source = "logoutTimestamp", target = "logoutTimestamp")
//    @Mapping(source = "sessionDuration", target = "sessionDuration")
//    @Mapping(source = "registration", target = "registration")
//    @Mapping(source = "itemUpdated", target = "itemUpdated")
//    @Mapping(source = "before", target = "before")
//    @Mapping(source = "after", target = "after")
//    @Mapping(source = "caseId", target = "caseId")
//    @Mapping(source = "claimantName", target = "claimantName")
//    @Mapping(source = "endTimestamp", target = "endTimestamp")
//    @Mapping(source = "viewDuration", target = "viewDuration")
//    @Mapping(source = "device", target = "device")
//    @Mapping(source = "browser", target = "browser")
//    @Mapping(source = "incidentType", target = "incidentType")
//    @Mapping(source = "incidentDetails", target = "incidentDetails")
//    @Mapping(source = "systemComponent", target = "systemComponent")
//    @Mapping(source = "errorCode", target = "errorCode")
//    @Mapping(source = "errorMessage", target = "errorMessage")
//    @Mapping(source = "resolved", target = "resolved")
//    @Mapping(source = "resolutionDetails", target = "resolutionDetails")
//    @Mapping(source = "actionType", target = "actionType")
//    @Mapping(source = "eventOffset", target = "eventOffset")
    AuditLogAccessDTO toDTO(AuditLogAccess auditLogAccess);

//    @Mapping(source = "timestamp", target = "timestamp")
//    @Mapping(source = "accessType", target = "accessType")
//    @Mapping(source = "agent", target = "agent")
//    @Mapping(source = "agentId", target = "agentId")
//    @Mapping(source = "role", target = "role")
//    @Mapping(source = "ipAddress", target = "ipAddress")
//    @Mapping(source = "uniqueCaseId", target = "uniqueCaseId")
//    @Mapping(source = "actor", target = "actor")
//    @Mapping(source = "action", target = "action")
//    @Mapping(source = "from", target = "from")
//    @Mapping(source = "to", target = "to")
//    @Mapping(source = "file", target = "file")
//    @Mapping(source = "sessionId", target = "sessionId")
//    @Mapping(source = "logoutTimestamp", target = "logoutTimestamp")
//    @Mapping(source = "sessionDuration", target = "sessionDuration")
//    @Mapping(source = "registration", target = "registration")
//    @Mapping(source = "itemUpdated", target = "itemUpdated")
//    @Mapping(source = "before", target = "before")
//    @Mapping(source = "after", target = "after")
//    @Mapping(source = "caseId", target = "caseId")
//    @Mapping(source = "claimantName", target = "claimantName")
//    @Mapping(source = "endTimestamp", target = "endTimestamp")
//    @Mapping(source = "viewDuration", target = "viewDuration")
//    @Mapping(source = "device", target = "device")
//    @Mapping(source = "browser", target = "browser")
//    @Mapping(source = "incidentType", target = "incidentType")
//    @Mapping(source = "incidentDetails", target = "incidentDetails")
//    @Mapping(source = "systemComponent", target = "systemComponent")
//    @Mapping(source = "errorCode", target = "errorCode")
//    @Mapping(source = "errorMessage", target = "errorMessage")
//    @Mapping(source = "resolved", target = "resolved")
//    @Mapping(source = "resolutionDetails", target = "resolutionDetails")
//    @Mapping(source = "actionType", target = "actionType")
//    @Mapping(source = "eventOffset", target = "eventOffset")
    AuditLogAccess toEntity(AuditLogAccessDTO auditLogAccessDTO);
}
