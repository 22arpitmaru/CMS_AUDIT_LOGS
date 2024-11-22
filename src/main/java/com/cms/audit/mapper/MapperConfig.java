package com.cms.audit.mapper;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public AuditLogAccessMapper auditLogAccessMapper() {
        return Mappers.getMapper(AuditLogAccessMapper.class);
    }
}
