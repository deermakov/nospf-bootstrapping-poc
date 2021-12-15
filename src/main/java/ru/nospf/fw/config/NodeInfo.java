package ru.nospf.fw.config;

import lombok.Data;
import lombok.Getter;

/**
 * todo Document type ApplicationConfig
 */
@Data
public class NodeInfo {
    private String networkId;
    private Integer nodeBucket;
    private Integer port;
}
