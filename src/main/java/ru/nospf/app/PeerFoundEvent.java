package ru.nospf.app;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * todo Document type PeerFoundEvent
 */
@Data
@AllArgsConstructor
public class PeerFoundEvent {
    private String fullDnsPeerName;
}
