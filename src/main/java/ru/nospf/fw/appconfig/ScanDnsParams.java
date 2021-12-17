package ru.nospf.fw.appconfig;

import lombok.Data;

import java.util.List;

/**
 * todo Document type ApplicationConfig
 */
@Data
public class ScanDnsParams {
    private List<String> domains;
    private Integer firstPort;
    private Integer lastPort;
    private Integer lastNodeBucket;
}
