package ru.nospf.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.nospf.adapter.dns.PeerNameGenerator;
import ru.nospf.adapter.tcp.OutboundAdapter;
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

    @EventListener
    public void onPeerFoundEvent(PeerFoundEvent event) {
        log.info("Registering peer: {}", event);

        outboundAdapter.send("TcP mEsSaGe", event.getPeer());

        // Сохраняем в БД только после успешного получения ответа (без exception) от пира
        storage.save(event.getPeer());
    }
}
