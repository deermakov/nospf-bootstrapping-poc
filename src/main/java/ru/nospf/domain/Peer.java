package ru.nospf.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

/**
 * todo Document type Peer
 */
@Entity
@Data
public class Peer {
    @Id
    @GeneratedValue
    private UUID uuid;

    private String ip;
    private Integer port;
    private boolean malicious;
}
