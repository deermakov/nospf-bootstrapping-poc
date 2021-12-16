package ru.nospf.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

/**
 * todo Document type Peer
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Peer {
    @Id
    @GeneratedValue
    private UUID uuid;

    private String ip;
    private Integer port;
    private boolean malicious;
}
