package ru.nospf.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.nospf.app.api.Database;
import ru.nospf.app.api.OutboundAdapter;
import ru.nospf.domain.Peer;
import ru.nospf.fw.appconfig.ApplicationConfig;

import javax.annotation.PostConstruct;

/**
 * todo Document type PeerFinder
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class PeerRegistrator {

    private final ApplicationConfig applicationConfig;

    private final OutboundAdapter outboundAdapter;
    private final Database storage;

    @PostConstruct
    public void logSelfInfo() {
        log.info("Self node info: {}", applicationConfig.getNodeInfo());
        String selfNodeName = PeerNameGenerator.getPeerName(applicationConfig.getNodeInfo().getNetworkId(), applicationConfig.getNodeInfo().getNodeBucket(),
            applicationConfig.getNodeInfo().getPort());
        log.info("Self DNS name: {}", selfNodeName);
    }

    public void registerPeer(Peer peer) {
        log.info("Peer found, trying to communicate: {}", peer);

        String peerPublicKey = outboundAdapter.handshake(peer);

        // Сохраняем в БД только после успешного получения ответа от пира (если handshake() не выбросил exception)
        peer.setPublicKey(peerPublicKey);
        storage.save(peer);
    }
}
