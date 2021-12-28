package ru.nospf.adapter.dns;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.nospf.fw.appconfig.ApplicationConfig;

/**
 * todo Document type DnsNameProbe
 */
@Component
@Slf4j
public class DnsPeersScanner {

    private final ApplicationConfig applicationConfig;
    private final DnsPeerChecker dnsPeerChecker;

    private Integer nodeBucket;
    private Integer port;

    public DnsPeersScanner(ApplicationConfig applicationConfig,
        DnsPeerChecker dnsPeerChecker) {
        this.applicationConfig = applicationConfig;
        this.dnsPeerChecker = dnsPeerChecker;

        nodeBucket = 0;
        port = applicationConfig.getScanDnsParams().getFirstPort();
    }

    @Scheduled(fixedRate = 10)
    public void findNextNode() {

        String networkId = applicationConfig.getNodeInfo().getNetworkId();
        String domain = applicationConfig.getScanDnsParams().getDomains().get(0);

        // Проверяем вычисленное имя хоста
        dnsPeerChecker.checkPeer(networkId, domain, nodeBucket, port);

        // Вычисляем значения nodeBucket и port для следующей проверки
        if (port < applicationConfig.getScanDnsParams().getLastPort()) {
            port++;
        } else {
            port = applicationConfig.getScanDnsParams().getFirstPort();
            if (nodeBucket < applicationConfig.getScanDnsParams().getLastNodeBucket()) {
                nodeBucket++;
            } else {
                nodeBucket = 0;
            }
        }
    }
}
