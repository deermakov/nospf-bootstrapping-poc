package ru.nospf.fw.appconfig;

import lombok.Data;

/**
 * todo Document type ApplicationConfig
 */
@Data
public class NodeInfo {
    private String networkId;
    private Integer nodeBucket;
    private Integer port;
    private String publicKey;
}
