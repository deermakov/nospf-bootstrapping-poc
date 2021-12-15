package ru.nospf.fw.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * todo Document type ApplicationConfig
 */
@Configuration
@ConfigurationProperties(prefix = "nospf-bootstrapping-poc")
@Getter
public class ApplicationConfig {
    private NodeInfo nodeInfo;
    private ScanDnsParams scanDnsParams;
}
