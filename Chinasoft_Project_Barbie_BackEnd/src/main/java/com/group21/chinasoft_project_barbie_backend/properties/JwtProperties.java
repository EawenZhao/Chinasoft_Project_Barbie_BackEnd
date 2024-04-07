package com.group21.chinasoft_project_barbie_backend.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "chinasoft-project-barbie.jwt")
@Data
public class JwtProperties {
    private String secretKey;
    private Long ttl;
    private String tokenName;
}
