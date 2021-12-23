package ru.nospf.adapter.tcp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.nospf.app.api.OutboundAdapter;
import ru.nospf.domain.MessageType;
import ru.nospf.domain.Peer;
import ru.nospf.fw.intconfig.OutboundIntegrationConfig;

/**
 * todo Document type OutboundAdapter
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TcpOutboundAdapter implements OutboundAdapter {

    public final OutboundIntegrationConfig.ToTcpGateway gateway;

    @Override
    public String handshake(Peer peer) {
        log.debug("Sending handshake request");
        String peerPublicKey = gateway.send("", MessageType.HANDSHAKE, peer.getIp(), peer.getPort());
        log.debug("Received handshake response: peerPublicKey = {}", peerPublicKey);
        return peerPublicKey;
    }
}
