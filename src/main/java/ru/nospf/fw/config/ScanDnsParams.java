package ru.nospf.fw.config;

import lombok.Getter;

import java.util.List;

/**
 * todo Document type ApplicationConfig
 */
@Getter
public class ScanDnsParams {
    private List<String> domains;
    private Integer firstPort;
    private Integer lastPort;
    private Integer lastNodeBucket;
}
