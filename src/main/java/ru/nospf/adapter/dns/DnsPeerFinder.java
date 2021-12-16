package ru.nospf.adapter.dns;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.nospf.app.PeerFoundEvent;
import ru.nospf.domain.Peer;
import ru.nospf.fw.config.ApplicationConfig;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * todo Document type DnsNameProbe
 */
@Component
@Slf4j
public class DnsPeerFinder {

    private final ApplicationConfig applicationConfig;
    private final ApplicationEventPublisher applicationEventPublisher;

    private Integer nodeBucket;
    private Integer port;

    public DnsPeerFinder(ApplicationConfig applicationConfig, ApplicationEventPublisher applicationEventPublisher) {
        this.applicationConfig = applicationConfig;
        this.applicationEventPublisher = applicationEventPublisher;

        nodeBucket = 0;
        port = applicationConfig.getScanDnsParams().getFirstPort();
    }

    @Scheduled(fixedRate = 10)
    public void findNextNode() {

        // Собираем полное DNS имя хоста для очередной проверки по DNS
        String networkId = applicationConfig.getNodeInfo().getNetworkId();
        String nodeName = PeerNameGenerator.getPeerName(networkId, nodeBucket, port);
        String domain = applicationConfig.getScanDnsParams().getDomains().get(0);
        String fullDnsPeerName = nodeName + "." + domain;

        log.debug("Checking for existence: networkId = {}, domain = {}, nodeBucket = {}, port = {}", networkId, domain, nodeBucket, port);

        // Проверяем вычисленное имя хоста
        String ip = getPeerIp(fullDnsPeerName);
        if (ip != null) {
            Peer peer = Peer.builder().ip(ip).port(port).build();
            applicationEventPublisher.publishEvent(new PeerFoundEvent(fullDnsPeerName, peer));
        }

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

    // DNS узла = <hash>.<dynamicdns-domain>, например sf6c76a.dyndns.net

    // Проверяем наличие A и AAAA записей DNS.
    // При необходимости проверки других типов записей нужно использовать JNDI DNS provider: Attribute attr = new InitialDirContext().getAttributes("dns:_netblocks.google.com", new String[] {"TXT"}).get("TXT");
    public String getPeerIp(String fullDnsHostName) {
        try {
            InetAddress dnsresult = InetAddress.getByName(fullDnsHostName);
            String ip = dnsresult.getHostAddress();
            return ip;
        } catch (UnknownHostException e) {
            return null;
        }
    }
}
