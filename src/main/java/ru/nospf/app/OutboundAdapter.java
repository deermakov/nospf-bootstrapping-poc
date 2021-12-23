package ru.nospf.app;

import ru.nospf.domain.Peer;

/**
 * todo Document type OutboundAdapter
 */
public interface OutboundAdapter {
    String handshake(Peer peer);
}
