package ru.nospf.app;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.nospf.domain.Peer;

/**
 * todo Document type PeerFoundEvent
 */
@Data
@AllArgsConstructor
public class PeerFoundEvent {
    private String fullDnsPeerName;
    private Peer peer;
}
