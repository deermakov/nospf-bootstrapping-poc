package ru.nospf.fw.appconfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * todo Document type ApplicationConfig
 */
@Configuration
@ConfigurationProperties(prefix = "nospf-bootstrapping-poc")
@Data
public class ApplicationConfig {
    private NodeInfo nodeInfo;
    private ScanDnsParams scanDnsParams;
}
