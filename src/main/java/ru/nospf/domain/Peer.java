package ru.nospf.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * todo Document type Peer
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Peer {
    private UUID uuid;

    private String ip;
    private Integer port;
    private boolean malicious;
}
