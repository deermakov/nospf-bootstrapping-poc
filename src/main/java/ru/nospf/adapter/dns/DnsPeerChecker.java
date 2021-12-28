package ru.nospf.adapter.dns;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ru.nospf.app.PeerNameGenerator;
import ru.nospf.app.PeerRegistrator;
import ru.nospf.domain.Peer;
import ru.nospf.fw.appconfig.ApplicationConfig;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * todo Document type DnsNameProbe
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DnsPeerChecker {

    private final ApplicationConfig applicationConfig;
    private final PeerRegistrator peerRegistrator;

    @Async
    public void checkPeer(String networkId, String domain, Integer nodeBucket, Integer port) {

        // Собираем полное DNS имя хоста для очередной проверки по DNS
        String nodeName = PeerNameGenerator.getPeerName(networkId, nodeBucket, port);
        String fullDnsPeerName = nodeName + "." + domain;

        log.debug("Checking for existence: networkId = {}, domain = {}, nodeBucket = {}, port = {}", networkId, domain, nodeBucket, port);

        // Проверяем вычисленное имя хоста
        String ip = searchForPeerIp(fullDnsPeerName);
        if (ip != null) {
            Peer peer = Peer.builder().ip(ip).port(port).build();
            peerRegistrator.registerPeer(peer);
        }
    }

    // DNS узла = <hash>.<dynamicdns-domain>, например sf6c76a.dyndns.net

    // Проверяем наличие A и AAAA записей DNS.
    // При необходимости проверки других типов записей нужно использовать JNDI DNS provider: Attribute attr = new InitialDirContext().getAttributes("dns:_netblocks.google.com", new String[] {"TXT"}).get("TXT");
    private String searchForPeerIp(String fullDnsHostName) {
        try {
            InetAddress dnsresult = InetAddress.getByName(fullDnsHostName);
            String ip = dnsresult.getHostAddress();
            return ip;
        } catch (UnknownHostException e) {
            return null;
        }
    }
}
