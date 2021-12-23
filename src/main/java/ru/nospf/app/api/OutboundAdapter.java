package ru.nospf.app.api;

import ru.nospf.domain.Peer;

/**
 * todo Document type OutboundAdapter
 */
public interface OutboundAdapter {
    String handshake(Peer peer);
}
