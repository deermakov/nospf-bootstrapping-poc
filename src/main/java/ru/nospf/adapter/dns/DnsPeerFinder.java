package ru.nospf.adapter.dns;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.nospf.app.PeerFoundEvent;
import ru.nospf.fw.config.ApplicationConfig;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * todo Document type DnsNameProbe
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DnsPeerFinder {

    private final ApplicationConfig applicationConfig;
    private ApplicationEventPublisher applicationEventPublisher;

    private Integer nodeBucket = 0;
    private Integer port = applicationConfig.getScanDnsParams().getFirstPort();

    @Scheduled(fixedRate = 100)
    public void findNextNode() {

        // Собираем полное DNS имя хоста для очередной проверки по DNS
        String nodeName = NodeNameGenerator.getNodeName(applicationConfig.getNodeInfo().getNetworkId(), nodeBucket, port);
        String fullDnsPeerName = nodeName + "." + applicationConfig.getScanDnsParams().getDomains().get(0);

        // Проверяем вычисленное имя хоста
        if (isDnsNameExist(fullDnsPeerName)) {
            applicationEventPublisher.publishEvent(new PeerFoundEvent(fullDnsPeerName));
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
    public boolean isDnsNameExist(String fullDnsHostName) {
        try {
            InetAddress dnsresult[] = InetAddress.getAllByName(fullDnsHostName);
            return dnsresult.length > 0;
        } catch (UnknownHostException e) {
            return false;
        }
    }
}
