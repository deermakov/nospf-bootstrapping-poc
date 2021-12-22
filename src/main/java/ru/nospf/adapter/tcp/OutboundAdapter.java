package ru.nospf.adapter.tcp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.nospf.domain.Peer;
import ru.nospf.fw.intconfig.OutboundIntegrationConfig;

/**
 * todo Document type OutboundAdapter
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class OutboundAdapter {

    public final OutboundIntegrationConfig.ToTcpGateway gateway;

    public String handshake(Peer peer) {
        log.debug("Sending handshake request");
        String peerPublicKey = gateway.send("", MessageType.HANDSHAKE, peer.getIp(), peer.getPort());
        log.debug("Received handshake response: peerPublicKey = {}", peerPublicKey);
        return peerPublicKey;
    }
}
