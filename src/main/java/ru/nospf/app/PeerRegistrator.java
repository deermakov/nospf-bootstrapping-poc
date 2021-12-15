package ru.nospf.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * todo Document type PeerFinder
 */
@Component
@Slf4j
public class PeerRegistrator {

    @EventListener
    public void onPeerFoundEvent(PeerFoundEvent event) {
        log.info("Peer found: {}", event.getFullDnsPeerName());
    }
}
